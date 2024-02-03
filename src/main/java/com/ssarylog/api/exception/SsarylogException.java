package com.ssarylog.api.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public abstract class SsarylogException extends RuntimeException{

    public final Map<String, String> validation = new HashMap<>();

    public SsarylogException(String message) {
        super(message);
    }

    public SsarylogException(String message, Throwable cause) {
        super(message, cause);
    }

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }

    public abstract int getStatusCode();
}
