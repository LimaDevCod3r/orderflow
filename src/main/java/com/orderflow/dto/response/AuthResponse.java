package com.orderflow.dto.response;

import com.orderflow.domain.enums.Role;

import java.util.UUID;

public record AuthResponse(
        UUID id,
        String email,
        boolean isActive,
        Role role
) {
}
