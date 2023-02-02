package com.ecquaria.gds.exception;

public class ColumnMismatchException extends IllegalArgumentException{
    public ColumnMismatchException(String errorMessage) {
        super(errorMessage);
    }

}
