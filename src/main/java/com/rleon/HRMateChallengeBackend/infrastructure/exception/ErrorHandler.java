package com.rleon.HRMateChallengeBackend.infrastructure.exception;

import com.rleon.HRMateChallengeBackend.infrastructure.exception.custom.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@Slf4j
public class ErrorHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({BadRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ErrorMessage beerNotFoundException(BadRequestException ex) {
        log.error("[ErrorHandler:beerNotFoundException] " + ex.getErrorDescription());
        return ErrorMessage.builder().errorCode(ex.getErrorCode()).errorDescription(ex.getErrorDescription()).build();
    }

    @ExceptionHandler({FileNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    protected ErrorMessage beerNotFoundException(FileNotFoundException ex) {
        log.error("[ErrorHandler:beerNotFoundException] " + ex.getMessage());
        return ErrorMessage.builder().errorCode(HttpStatus.NOT_FOUND.value()).errorDescription(ex.getMessage()).build();
    }


    @NonNull
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        final BindingResult bindingResult = ex.getBindingResult();
        List<ErrorMessage> errorList = new ArrayList<>(1);
        if (bindingResult.hasErrors()) {
            final List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrorList) {
                String field = fieldError.getField();
                errorList.add(ErrorMessage.builder().errorDescription(field + ": " + fieldError.getDefaultMessage()).errorCode(HttpStatus.BAD_REQUEST.value()).build());
            }
        }
        return new ResponseEntity<>(errorList, headers, status);
    }


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected ErrorMessage handleUnknownException(Exception ex) {
        return ErrorMessage.builder().errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).errorDescription(Optional.ofNullable(ex.getMessage()).orElse(ex.getMessage())).build();
    }

}
