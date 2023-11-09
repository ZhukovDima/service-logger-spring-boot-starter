package com.demo.devsrc.filemanager.web;

import com.demo.devsrc.filemanager.error.IllegalRequestDataException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({IllegalRequestDataException.class, MultipartException.class})
    public ResponseEntity<?> conflict(HttpServletRequest req, Exception ex) {
        log.error("Exception", ex);
        return createResponseEntity(ex, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> internalError(HttpServletRequest req, Exception ex) {
        log.error("Exception", ex);
        return createResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<?> createResponseEntity(Exception ex, HttpStatus status) {
        Throwable rootCause = NestedExceptionUtils.getRootCause(ex);
        Map<String, Object> body = new HashMap<>();
        body.put("error", status.getReasonPhrase());
        body.put("message", ex.getClass().getSimpleName());
        body.put("detail", rootCause != null ? rootCause.getMessage() : ex.getMessage());
        return new ResponseEntity<>(body, status);
    }
}
