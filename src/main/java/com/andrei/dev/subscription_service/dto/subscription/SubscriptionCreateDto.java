package com.andrei.dev.subscription_service.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionCreateDto {
    @Schema(description = "Name of the subscription service", example = "Netflix")
    @NotBlank(message = "Service name is required")
    private String serviceName;

    @Schema(description = "Start date of the subscription", example = "2025-01-01")
    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @Schema(description = "End date of the subscription", example = "2025-06-30")
    private LocalDate endDate;
}
