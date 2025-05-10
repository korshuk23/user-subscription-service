package com.andrei.dev.subscription_service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    @Schema(description = "Email address (must be unique)", example = "john@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "First name of the user", example = "John", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;
}
