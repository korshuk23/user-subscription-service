package com.andrei.dev.subscription_service.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @Schema(description = "Username (must be unique)", example = "john_doe")
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Schema(description = "Email address (must be unique)", example = "john@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Schema(description = "Password", example = "StrongP@ssw0rd")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @Schema(description = "First name of the user", example = "John")
    @Size(max = 50, message = "First name must be at most 50 characters")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    @Size(max = 50, message = "Last name must be at most 50 characters")
    private String lastName;

}
