package com.orderflow.dto.response;

import com.orderflow.domain.enums.Role;

import java.util.UUID;

public record UserResponse(
        UUID id,
        String email,
        boolean isActive,
        Role role
) {
}
