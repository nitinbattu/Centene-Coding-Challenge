package com.healthcare.app.backend.model;

import lombok.Value;

@Value
public class Response {
    private Boolean status;
    private long statusCode;
    private String message;

    private Object extraData;
}
