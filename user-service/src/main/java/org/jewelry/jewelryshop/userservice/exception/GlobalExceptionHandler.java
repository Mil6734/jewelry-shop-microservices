package org.jewelry.jewelryshop.userservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

/**
 * Глобальный обработчик ошибок: валидация, 404, 500 и тд
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Ошибки валидации @Valid */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidation(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(err -> {
            String field = ((FieldError) err).getField();
            String msg   = err.getDefaultMessage();
            errors.put(field, msg);
        });

        Map<String, Object> body = Map.of(
                "status", HttpStatus.BAD_REQUEST.value(),
                "errors", errors
        );
        return ResponseEntity.badRequest().body(body);
    }

    /** ResponseStatusException (404, 409 и тд) */
    @ExceptionHandler(ResponseStatusException.class)
    protected ResponseEntity<Object> handleResponseStatus(ResponseStatusException ex) {
        Map<String, Object> body = Map.of(
                "status", ex.getStatusCode().value(),
                "error", ex.getReason()
        );
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    /** Всё остальное, возвращаем 500 и простой ответ */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleAll(Exception ex) {
        Map<String, Object> body = Map.of(
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "error", "Internal server error"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }
}
