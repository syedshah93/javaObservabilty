package com.mcds.observabilty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public Map<String,String> handleNoSuchElementException(
            NoSuchElementException ex){
        Map<String, String> errors = new HashMap<>();

        errors.put(ex.getMessage(),"E404");

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomException.class)
    public Map<String,String> handleCustomException(
            CustomException ex){
        Map<String, String> errors = new HashMap<>();

        errors.put(ex.getMessage(),ex.getErrorCode());

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(S3Exception.class)
    public Map<String,String> handleS3ExceptionException(
            S3Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getMessage(),"E1010");
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public Map<String,String> handleException(
            Exception ex){
        Map<String, String> errors = new HashMap<>();
        errors.put(ex.getMessage(),"Something Went Wrong !!");
        return errors;
    }
}
