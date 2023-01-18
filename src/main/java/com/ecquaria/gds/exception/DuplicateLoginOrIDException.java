package com.ecquaria.gds.exception;

public class DuplicateLoginOrIDException extends Exception{
    public DuplicateLoginOrIDException(String errorMessage) {
        super(errorMessage);
    }
}
