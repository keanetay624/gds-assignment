package com.ecquaria.gds.exception;

public class MissingRequiredFieldsException extends IllegalArgumentException{
    public MissingRequiredFieldsException(String errorMessage) {
        super(errorMessage);
    }
}
