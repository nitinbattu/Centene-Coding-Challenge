package com.healthcare.app.backend.service.impl;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.*;
import com.healthcare.app.backend.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.healthcare.app.backend.util.Constants.ERROR_RESPONSE_CODE;
import static com.healthcare.app.backend.util.CommonUtils.mkResponse;
import static com.healthcare.app.backend.util.Constants.SUCCESS_RESPONSE_CODE;

@Service
@Slf4j
public class EnrollmentServiceImpl implements EnrollmentService {

    @Autowired
    DataOperations<Enrollee, String> enrolleeDao;

    @Autowired
    DataOperations<Dependent, String> enrolleeDepDao;

    @Autowired
    public EnrollmentServiceImpl(DataOperations<Enrollee, String> enrolleeDao, DataOperations<Dependent, String> enrolleeDepDao){
        this.enrolleeDao = enrolleeDao;
        this.enrolleeDepDao = enrolleeDepDao;
    }

    @Override
    public Map.Entry<Response, Integer> addEnrollee(Enrollee newEnrollee) {
        log.info("Enrollee is : " + newEnrollee);
        try {
            checkArgument(newEnrollee.getName() != null, "Name can't be null");
            checkArgument(newEnrollee.getDob() != null, "Date of birth can't be null");
            checkArgument(newEnrollee.getActivationStatus() != null, "Activation status can't be null");
        } catch (IllegalArgumentException exception){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, exception.getMessage(), new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }

        String enrolleeId = enrolleeDao.createRecord(newEnrollee);
        return mkResponse(new Response(true, SUCCESS_RESPONSE_CODE, "", new IDField(enrolleeId)), HttpStatus.OK.value());
    }

    @Override
    public Map.Entry<Response, Integer> updateEnrollee(String enrolleeId, Enrollee updates) {
        try {
            checkArgument(enrolleeId != null && !enrolleeId.isEmpty(), "Enrollee id can't be null or empty");
        } catch (IllegalArgumentException exception){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, exception.getMessage(), new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }

        log.info("Update for enrollee : " + enrolleeId + " are : " + updates);

        updates.setId(enrolleeId);
        boolean status = enrolleeDao.updateRecord(updates);
        if(!status){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, "Unable to update enrollee details, enrollee doesn't exists", new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }

        return mkResponse(new Response(true, SUCCESS_RESPONSE_CODE, "", new EmptyJsonBody()), HttpStatus.OK.value());
    }

    @Override
    public Map.Entry<Response, Integer> deleteEnrollee(String enrolleeId) {
        try {
            checkArgument(enrolleeId != null && !enrolleeId.isEmpty(), "Enrollee id can't be null or empty");
        } catch (IllegalArgumentException exception){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, exception.getMessage(), new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }
        Enrollee enrollee = enrolleeDao.fetchRecord(enrolleeId);
        if(enrollee == null){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, "Enrollee not found", new EmptyJsonBody()), HttpStatus.NOT_FOUND.value());
        }

        List<String> dependents = enrollee.getDependents();
        boolean status = enrolleeDao.deleteRecord(enrolleeId);
        if(!status){
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, "Unable to delete enrollee through ID", new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }
        if(dependents != null) {
            for (String dependentId : dependents) {
                enrolleeDepDao.deleteRecord(dependentId);
            }
        }

        return mkResponse(new Response(true, SUCCESS_RESPONSE_CODE, "", new EmptyJsonBody()), HttpStatus.OK.value());
    }
}
