package com.backend.medconsult.controller.appointment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Global exception handler for the Appointment module.
 * Catches validation, business rule, access denial, and generic errors,
 * returning structured JSON error responses.
 */
@RestControllerAdvice(basePackages = "com.backend.medconsult.controller.appointment")
public class AppointmentExceptionHandler {

    // ─── Validation errors (JSR-303) ──────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> fieldErrors = new HashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fe.getField(), fe.getDefaultMessage());
        }

        Map<String, Object> body = buildErrorBody(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                fieldErrors.toString());

        return ResponseEntity.badRequest().body(body);
    }

    // ─── Business-rule errors ──────────────────────────────────────────

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();

        // Map well-known phrases to appropriate HTTP status codes
        HttpStatus status;
        if (message != null && (message.contains("not found") || message.contains("No patient profile"))) {
            status = HttpStatus.NOT_FOUND;
        } else if (message != null && (message.contains("not available")
                || message.contains("already cancelled")
                || message.contains("already has an active")
                || message.contains("Cannot cancel")
                || message.contains("Cannot update"))) {
            status = HttpStatus.CONFLICT;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }

        return ResponseEntity.status(status).body(
                buildErrorBody(status.value(), message, null));
    }

    // ─── Access denied ─────────────────────────────────────────────────

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                buildErrorBody(HttpStatus.FORBIDDEN.value(), "Access denied", null));
    }

    // ─── Catch-all ─────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                buildErrorBody(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "An unexpected error occurred", null));
    }

    // ─── Helper ────────────────────────────────────────────────────────

    private Map<String, Object> buildErrorBody(int status, String message, String detail) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", status);
        body.put("error", message);
        if (detail != null) {
            body.put("detail", detail);
        }
        return body;
    }
}
