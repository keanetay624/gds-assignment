package com.ecquaria.gds.exception;

public class InvalidIdException extends IllegalArgumentException {
    public InvalidIdException(String errorMessage) {
        super(errorMessage);
    }
}
