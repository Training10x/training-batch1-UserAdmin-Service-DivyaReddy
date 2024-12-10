package com.example.userapp.mapper;

import com.example.userapp.dto.UserResponse;
import com.example.userapp.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "status", target = "statusisEnabled")
    User toEntity(UserResponse userResponse);

    @Mapping(source = "statusisEnabled", target = "status")
    UserResponse toDTO(User user);
}
