package com.bpm.mrceprocess.exception;

import com.bpm.dtos.BaseResponse;
import com.bpm.enums.ApplicationMessage;
import com.bpm.exception.ApplicationException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

/**
 * Global exception handler for handling application-wide exceptions.
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String FORMAT_LOG_ERROR = "[ERROR] URL: {}, Message: {}";

    /**
     * Handles ApplicationException and IOException.
     *
     * @param error   the exception thrown.
     * @param request the current HTTP request.
     * @return a standardized error response.
     */
    @ExceptionHandler({ApplicationException.class, IOException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<Object>> handleApplicationError(ApplicationException error, ServletRequest request) {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        log.error(FORMAT_LOG_ERROR, url, error.getMessage(), error);

        BaseResponse<Object> response = BaseResponse.failure(error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles IllegalArgumentException (e.g., invalid input data).
     *
     * @param error   the exception thrown.
     * @param request the current HTTP request.
     * @return a standardized error response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<BaseResponse<Object>> handleIllegalArgumentError(IllegalArgumentException error, ServletRequest request) {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        log.warn(FORMAT_LOG_ERROR, url, error.getMessage());

        BaseResponse<Object> response = BaseResponse.failure(ApplicationMessage.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Handles NullPointerException (e.g., accessing null variables).
     *
     * @param error   the exception thrown.
     * @param request the current HTTP request.
     * @return a standardized error response.
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<Object>> handleNullPointerError(NullPointerException error, ServletRequest request) {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        log.error(FORMAT_LOG_ERROR, url, "NullPointerException detected", error);

        BaseResponse<Object> response = BaseResponse.failure(ApplicationMessage.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles generic exceptions that are not explicitly defined.
     *
     * @param error   the exception thrown.
     * @param request the current HTTP request.
     * @return a standardized error response.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<Object>> handleGenericError(Exception error, ServletRequest request) {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        log.error(FORMAT_LOG_ERROR, url, "Unhandled Exception", error);

        BaseResponse<Object> response = BaseResponse.failure(ApplicationMessage.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * Handles any unexpected errors (e.g., OutOfMemoryError).
     *
     * @param error   the throwable thrown.
     * @param request the current HTTP request.
     * @return a standardized error response.
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<BaseResponse<Object>> handleThrowableError(Throwable error, ServletRequest request) {
        String url = ((HttpServletRequest) request).getRequestURL().toString();
        log.error(FORMAT_LOG_ERROR, url, "Critical Error", error);

        BaseResponse<Object> response = BaseResponse.failure(ApplicationMessage.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}