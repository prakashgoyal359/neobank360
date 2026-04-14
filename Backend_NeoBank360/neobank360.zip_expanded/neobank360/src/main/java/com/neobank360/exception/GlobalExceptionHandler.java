package com.neobank360.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice // ✅ global exception handler
public class GlobalExceptionHandler {

    // ✅ Handle 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDenied() {
        return ResponseEntity
                .status(403)
                .body("Access Denied: Admin only");
    }
}