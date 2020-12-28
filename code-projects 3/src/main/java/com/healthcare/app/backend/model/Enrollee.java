package com.healthcare.app.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Data
public class Enrollee {

    private String id;
    private String name;

    @JsonProperty(value = "actvStatus")
    private Boolean activationStatus;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;

    @JsonProperty(value = "phoneNumber")
    private String phoneNumber;

    private List<String> dependents;

    public Enrollee mergeObject(Enrollee anotherObj){
        if(anotherObj.getId() != null) this.id = anotherObj.getId();
        if(anotherObj.getName() != null) this.name = anotherObj.getName();
        if(anotherObj.getDependents() != null){
            if(this.dependents == null){
                this.dependents = new ArrayList<>();
            }
            this.dependents.addAll(anotherObj.getDependents());
            this.dependents = new ArrayList<>(new HashSet<>(this.dependents)); // unique dependents
        }
        if(anotherObj.getDob() != null) this.dob = anotherObj.getDob();
        if(anotherObj.getPhoneNumber() != null) this.phoneNumber = anotherObj.getPhoneNumber();
        if(anotherObj.getActivationStatus() != null) this.activationStatus = anotherObj.getActivationStatus();
        return this;
    }
}
