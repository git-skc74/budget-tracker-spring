package com.example.budgettrackerspring;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // type mismatch 500 (POST /entry?amount=abc)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Map<String, String> handleTypeMismatch(MethodArgumentTypeMismatchException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid value: " + e.getValue());
        return error;
    }

    // no parameter 400 (POST /entry)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Map<String, String> handleMissingParam(MissingServletRequestParameterException e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Missing required parameter: " + e.getParameterName());
        return error;
    }

    // internal server error
    @ExceptionHandler(Exception.class)
    public Map<String, String> handleGeneral(Exception e) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Internal server error");
        return error;
    }
}
