package br.com.clinica.api_pro.infra;

import java.time.LocalDateTime;

public record ApiResponse<T>(
    LocalDateTime timestamp,
    String message,
    T data
) {
}
