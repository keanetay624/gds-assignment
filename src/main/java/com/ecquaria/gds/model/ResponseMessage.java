package com.ecquaria.gds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ResponseMessage {
    private String message;
    private String error;

    public ResponseMessage(String message) {
        this.message = message;
    }
}
