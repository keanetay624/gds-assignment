package com.ecquaria.gds.exception;

public class InvalidSalaryFormatException extends IllegalArgumentException{
    public InvalidSalaryFormatException(String errorMessage) {
        super(errorMessage);
    }
}
