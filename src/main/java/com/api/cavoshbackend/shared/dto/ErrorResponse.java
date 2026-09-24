package com.api.cavoshbackend.shared.dto;

import java.time.Instant;
import java.util.List;

public record ErrorResponse(

        boolean success,
        String code,
        String message,
        String path,
        List<FieldErrorResponse> fieldErrors,
        Instant timestamp
) {
}
