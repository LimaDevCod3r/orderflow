package com.orderflow.controller;

import com.orderflow.dto.request.CreateUserRequestDTO;
import com.orderflow.dto.request.LoginRequestDTO;
import com.orderflow.dto.response.AuthResponse;
import com.orderflow.dto.response.UserResponse;
import com.orderflow.services.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(this.authService.findAll());
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(
            @Valid @RequestBody CreateUserRequestDTO requestDTO) {

        var service = authService.register(requestDTO);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO,
            HttpServletResponse response) {

        var authResponse = authService.login(loginRequestDTO);

        // 🔐 Access Token - 15 minutos
        addCookie(
                response,
                "accessToken",
                authResponse.accessToken(),
                15 * 60
        );

        // 🔐 Refresh Token - 7 dias
        addCookie(
                response,
                "refreshToken",
                authResponse.refreshToken(),
                7 * 24 * 60 * 60
        );

        return ResponseEntity.ok(authResponse);
    }

    private void addCookie(
            HttpServletResponse response,
            String name,
            String value,
            int maxAge) {

        Cookie cookie = new Cookie(name, value);

        cookie.setHttpOnly(true);   // impede acesso via JS
        cookie.setSecure(true);     // apenas HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        // 🔥 protege contra CSRF
        cookie.setAttribute("SameSite", "Strict");

        response.addCookie(cookie);
    }
}