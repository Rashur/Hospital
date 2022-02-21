package com.epam.hospital.handler;

import com.epam.hospital.error.ErrorResponse;
import com.epam.hospital.exception.NurseNotFoundException;
import com.epam.hospital.exception.PatientNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NurseNotFoundException.class,
            PatientNotFoundException.class})
    public ErrorResponse handleResourceNotFound(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponse.setError(HttpStatus.NOT_FOUND.toString());
        errorResponse.setMessage(exception.getMessage());
        return errorResponse;
    }
}
