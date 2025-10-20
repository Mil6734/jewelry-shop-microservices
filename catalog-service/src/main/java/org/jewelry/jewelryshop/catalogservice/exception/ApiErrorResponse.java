package org.jewelry.jewelryshop.catalogservice.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorResponse {
    private LocalDateTime timestamp;  // время ошибки
    private int status;               // HTTP статус
    private String errorCode;         // внутренний код ошибки
    private String message;           // сообщения об ошибке
    private String path;              // URL где произошла ошибка
}
