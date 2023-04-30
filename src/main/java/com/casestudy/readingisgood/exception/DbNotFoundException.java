package com.casestudy.readingisgood.exception;

import java.io.Serial;

public class DbNotFoundException extends Exception {
    @Serial
    private static final long serialVersionUID = 1L;

    public DbNotFoundException(String message) {
        super(message);
    }
}
