package com.healthcare.app.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Dependent {
    private String id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    public Dependent mergeObject(Dependent anotherObj){
        if(anotherObj.getId() != null) this.id = anotherObj.getId();
        if(anotherObj.getName() != null) this.name = anotherObj.getName();
        if(anotherObj.getDob() != null) this.dob = anotherObj.getDob();
        return this;
    }
}
