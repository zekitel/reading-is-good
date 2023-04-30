package com.casestudy.readingisgood.exception;

import java.io.Serial;

public class StartDateIsGreaterThanEndDateException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public StartDateIsGreaterThanEndDateException(String message) {
        super(message);
    }
}