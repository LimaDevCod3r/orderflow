package com.orderflow.services;

import com.orderflow.dto.request   .CreateUserRequestDTO;
import com.orderflow.dto.response.AuthResponse;
import com.orderflow.mapper.UserMapper;
import com.orderflow.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper mapper;

    public AuthService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, UserMapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = mapper;

    }


    public List<AuthResponse> findAll() {
        var users = userRepository.findAll();
        return mapper.toDTOList(users);
    }

    public AuthResponse register(CreateUserRequestDTO dto) {

        var user = userRepository.findByEmail(dto.email());

        if (user.isPresent()) {
            throw new IllegalStateException(String.format("User with email %s is already registered", dto.email()));
        }

        var entity = mapper.toEntity(dto);

        entity.setPassword(passwordEncoder.encode(dto.password()));

        return mapper.toResponse(userRepository.save(entity));
    }
}
