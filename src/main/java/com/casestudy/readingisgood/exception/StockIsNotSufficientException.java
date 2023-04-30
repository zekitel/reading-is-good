package com.casestudy.readingisgood.exception;

import java.io.Serial;

public class StockIsNotSufficientException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public StockIsNotSufficientException(String message) {
        super(message);
    }
}