package com.andrei.dev.subscription_service.mapper;

import com.andrei.dev.subscription_service.dto.UserCreateDto;
import com.andrei.dev.subscription_service.dto.UserResponseDto;
import com.andrei.dev.subscription_service.dto.UserUpdateDto;
import com.andrei.dev.subscription_service.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(@MappingTarget User user, UserUpdateDto dto);

    UserResponseDto toDto(User user);
}
