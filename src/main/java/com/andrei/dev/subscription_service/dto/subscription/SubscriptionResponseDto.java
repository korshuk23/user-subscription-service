package com.andrei.dev.subscription_service.dto.subscription;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionResponseDto {

    @Schema(description = "ID of the subscription", example = "42")
    private Long id;

    @Schema(description = "Name of the subscription service", example = "Netflix")
    private String serviceName;

    @Schema(description = "Start date of the subscription", example = "2025-01-01")
    private LocalDate startDate;

    @Schema(description = "End date of the subscription", example = "2025-06-30")
    private LocalDate endDate;

    @Schema(description = "Current status of the subscription", example = "active")
    private String status;

    @Schema(description = "Creation timestamp", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Last updated timestamp", example = "2025-03-01T15:30:00")
    private LocalDateTime updatedAt;
}
