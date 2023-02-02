package com.ecquaria.gds.exception;

public class DuplicateLoginOrIDException extends IllegalArgumentException{
    public DuplicateLoginOrIDException(String errorMessage) {
        super(errorMessage);
    }
}
