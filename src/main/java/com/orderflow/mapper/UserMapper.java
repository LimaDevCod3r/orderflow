package com.orderflow.mapper;

import com.orderflow.domain.entities.User;
import com.orderflow.dto.request.CreateUserRequestDTO;
import com.orderflow.dto.response.AuthResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    AuthResponse toResponse(User entity);

    User toEntity(CreateUserRequestDTO dto);

    List<AuthResponse> toDTOList(List<User> users);
}
