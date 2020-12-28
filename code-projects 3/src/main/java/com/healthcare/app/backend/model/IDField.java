package com.healthcare.app.backend.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Value;

@JsonSerialize
@Value
public class IDField {
    public String id;
}
