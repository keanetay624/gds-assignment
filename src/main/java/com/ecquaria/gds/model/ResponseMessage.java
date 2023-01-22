package com.ecquaria.gds.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ResponseMessage {
    private String message;
    private String error;
    private List<Employee> results;

    public ResponseMessage(String message, String error) {
        this.message = message;
        this.error = error;
    }

    public ResponseMessage(String message) {
        this.message = message;
    }
}
