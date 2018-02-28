package com.naosim.inmemorydb.exception;

public class DuplicatePrimaryKeyException extends RuntimeException {
    public DuplicatePrimaryKeyException(String message) {
        super(message);
    }
}
