package com.ecquaria.gds.exception;

public class LoginAlreadyExistsException extends IllegalArgumentException{
    public LoginAlreadyExistsException(String errorMessage) {
        super(errorMessage);
    }
}
