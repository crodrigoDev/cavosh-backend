package com.api.cavoshbackend.shared.dto;

public record FieldErrorResponse(

        String field,
        String message
) {
}
