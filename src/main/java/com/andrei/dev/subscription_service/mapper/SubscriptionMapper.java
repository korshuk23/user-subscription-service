package com.andrei.dev.subscription_service.mapper;

import com.andrei.dev.subscription_service.dto.subscription.SubscriptionCreateDto;
import com.andrei.dev.subscription_service.dto.subscription.SubscriptionResponseDto;
import com.andrei.dev.subscription_service.entity.Subscription;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubscriptionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", constant = "inactive")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Subscription toEntity(SubscriptionCreateDto dto);

    SubscriptionResponseDto toDto(Subscription subscription);
    }
