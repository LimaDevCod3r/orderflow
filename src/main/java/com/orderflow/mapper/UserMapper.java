package com.orderflow.mapper;

import com.orderflow.domain.entities.User;
import com.orderflow.dto.request.CreateUserRequestDTO;
import com.orderflow.dto.response.UserResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User entity);

    User toEntity(CreateUserRequestDTO dto);

    List<UserResponse> toDTOList(List<User> users);
}
