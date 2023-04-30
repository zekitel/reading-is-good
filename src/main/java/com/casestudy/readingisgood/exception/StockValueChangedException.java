package com.casestudy.readingisgood.exception;

import java.io.Serial;

public class StockValueChangedException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;
    public static final int STATUS_CODE = 410;

    public StockValueChangedException(String message) {
        super(message);
    }
}