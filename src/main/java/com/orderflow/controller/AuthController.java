package com.orderflow.controller;

import com.orderflow.dto.request.CreateUserRequestDTO;
import com.orderflow.dto.response.AuthResponse;
import com.orderflow.services.AuthService;
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
    public ResponseEntity<List<AuthResponse>> findAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(this.authService.findAll());
    }


    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUser(@Valid @RequestBody CreateUserRequestDTO requestDTO) {

        var service = authService.register(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(service);
    }
}
