package com.mycompany.app.service;

/**
 * Wyjątek reprezentujący błędy podczas integracji z API.
 */
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}