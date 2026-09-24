package com.api.cavoshbackend.shared.dto;

import java.time.Instant;

public record ApiResponse<T>(

        boolean success,
        String message,
        T data,
        Instant timestamp

) {
}
