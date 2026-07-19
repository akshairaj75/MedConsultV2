package com.backend.medconsult.controller.clinic;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Clinic-scoped exception handler.
 *
 * <p>Maps common runtime exceptions to structured JSON responses so that API
 * consumers receive clear, consistent error messages instead of raw Spring
 * stack traces.
 *
 * <p>Error body format:
 * <pre>{@code
 * {
 *   "timestamp": "2026-07-19T10:00:00",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "...",
 *   "path": "/api/medconsult/clinics/..."
 * }
 * }</pre>
 */
@RestControllerAdvice(assignableTypes = {
        ClinicController.class,
        ClinicAdminController.class
})
public class ClinicExceptionHandler {

    // ── 404 Not Found ─────────────────────────────────────────────────

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            RuntimeException ex, HttpServletRequest request) {

        String msg = ex.getMessage() != null ? ex.getMessage() : "Resource not found";

        // Decide status: if it's a "not found" message use 404, else 500
        HttpStatus status = (msg.toLowerCase().contains("not found"))
                ? HttpStatus.NOT_FOUND : HttpStatus.INTERNAL_SERVER_ERROR;

        return buildError(status, msg, request.getRequestURI());
    }

    // ── 409 Conflict — duplicate resource ─────────────────────────────

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleConflict(
            IllegalArgumentException ex, HttpServletRequest request) {

        return buildError(HttpStatus.CONFLICT, ex.getMessage(), request.getRequestURI());
    }

    // ── 400 Bad Request — bean validation failures ────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {

        List<String> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .collect(Collectors.toList());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Validation Failed");
        body.put("errors", fieldErrors);
        body.put("path", request.getRequestURI());

        return ResponseEntity.badRequest().body(body);
    }

    // ── Private builder ───────────────────────────────────────────────

    private ResponseEntity<Map<String, Object>> buildError(
            HttpStatus status, String message, String path) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);

        return ResponseEntity.status(status).body(body);
    }
}
