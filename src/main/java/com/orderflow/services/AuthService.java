package com.orderflow.services;

import com.orderflow.dto.request.CreateUserRequestDTO;
import com.orderflow.dto.request.LoginRequestDTO;
import com.orderflow.dto.response.AuthResponse;
import com.orderflow.dto.response.UserResponse;
import com.orderflow.mapper.UserMapper;
import com.orderflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserMapper mapper;

    public List<UserResponse> findAll() {
        var users = userRepository.findAll();
        return mapper.toDTOList(users);
    }

    public UserResponse register(CreateUserRequestDTO dto) {

        var user = userRepository.findByEmail(dto.email());

        if (user.isPresent()) {
            throw new IllegalStateException(String.format("User with email %s is already registered", dto.email()));
        }

        var entity = mapper.toEntity(dto);

        entity.setPassword(passwordEncoder.encode(dto.password()));

        return mapper.toResponse(userRepository.save(entity));
    }

    public AuthResponse login(LoginRequestDTO dto) {

        // 1️⃣ Buscar usuário pelo email
        var user = userRepository.findByEmail(dto.email())
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid email or password"));

        // 2️⃣ Validar senha
        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        // 3️⃣ Gerar tokens
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        // 4️⃣ Criar DTO de resposta do usuário
        UserResponse userResponse = mapper.toResponse(user);

        return new AuthResponse(
                userResponse,
                accessToken,
                refreshToken
        );
    }
}
