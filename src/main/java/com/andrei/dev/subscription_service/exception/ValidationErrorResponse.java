package com.andrei.dev.subscription_service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> validationErrors;

    public ValidationErrorResponse(LocalDateTime timestamp, int status, String error,
                                   String path, Map<String, String> validationErrors) {
        super(timestamp, status, error, "Validation failed", path);
        this.validationErrors = validationErrors;
    }
}