package com.acme.catalog.backend.mapper;

import com.acme.catalog.backend.dto.UserRegistrationResponseDto;
import com.acme.catalog.backend.dto.UserRequestDto;
import com.acme.catalog.backend.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserRegistrationResponseDto toUserRegistrationResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    User toUser(UserRequestDto userRequestDtoDto);
}