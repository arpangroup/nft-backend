package com.arpangroup.referral_service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
@Slf4j
public class BonusGlobalExceptionHandler {
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<String> handleClientError(HttpClientErrorException ex) {
        log.error("Downstream error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
        ex.printStackTrace();
        return ResponseEntity
                .status(ex.getStatusCode())
                .body("Client error from downstream: " + ex.getResponseBodyAsString());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<String> handleServerError(HttpServerErrorException ex) {
        log.error("Downstream error: status={}, body={}", ex.getStatusCode(), ex.getResponseBodyAsString());
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body("Downstream service failed: " + ex.getResponseBodyAsString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherErrors(Exception ex) {
        log.error("Exception Occurred: {}", ex.getMessage());
        ex.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Unexpected error: " + ex.getMessage());
    }
}
