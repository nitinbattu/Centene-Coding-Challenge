package com.healthcare.app.backend.service.impl;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.EmptyJsonBody;
import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.EnrolleeDependentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkArgument;
import static com.healthcare.app.backend.util.CommonUtils.mkResponse;
import static com.healthcare.app.backend.util.Constants.ERROR_RESPONSE_CODE;
import static com.healthcare.app.backend.util.Constants.SUCCESS_RESPONSE_CODE;

@Service
@Slf4j
public class EnrolleeDependentServiceImpl implements EnrolleeDependentService {

    @Autowired
    DataOperations<Dependent, String> enrolleeDepDao;

    @Autowired
    DataOperations<Enrollee, String> enrolleeDao;

    @Autowired
    public EnrolleeDependentServiceImpl(DataOperations<Enrollee, String> enrolleeDao, DataOperations<Dependent, String> enrolleeDepDao){
        this.enrolleeDao = enrolleeDao;
        this.enrolleeDepDao = enrolleeDepDao;
    }

    @Override
    public Map.Entry<Response, Integer> addDependents(String enrolleeId, List<Dependent> dependents) {
        if (enrolleeId == null || enrolleeId.isEmpty()) {
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, "Enrollee ID can't be null", new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }

        boolean skippedSome = false;
        List<String> dependentIds = new ArrayList<>();
        for (Dependent dependent : dependents) {
            try {
                checkArgument(dependent.getName() != null, "Dependent name can't be null");
                checkArgument(dependent.getDob() != null, "Date of Birth can't be null");
                String dependentId = enrolleeDepDao.createRecord(dependent);
                dependent.setId(dependentId);
                dependentIds.add(dependentId);
            } catch (IllegalArgumentException exception) {
                log.error(exception.getMessage() + " for dependent " + dependent);
                skippedSome = true;
            }
        }

        Enrollee updates = new Enrollee();
        updates.setId(enrolleeId);
        updates.setDependents(dependentIds);
        boolean status = enrolleeDao.updateRecord(updates);

        if (skippedSome) {
            return mkResponse(
                    new Response(status, status ? SUCCESS_RESPONSE_CODE : ERROR_RESPONSE_CODE,
                            status ? "Skipped some dependents because they missing name or DOB or both" : "Unable to update Dependents, enrollee ID doesn't exists",
                            dependents), status ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value());
        }

        return mkResponse(
                new Response(status, status ? SUCCESS_RESPONSE_CODE : ERROR_RESPONSE_CODE,
                        status ? "" : "Unable to update Dependents, enrollee ID doesn't exists",
                        dependents), status ? HttpStatus.OK.value() : HttpStatus.NOT_FOUND.value());
    }

    @Override
    public Map.Entry<Response, Integer> updateDependents(List<Dependent> dependents) {

        boolean skippedSome = false;
        boolean updateFailedForSome = false;
        for (Dependent dependent : dependents) {
            try {
                checkArgument(dependent.getId() != null, "Dependent ID not found for updating dependent");
                boolean status = enrolleeDepDao.updateRecord(dependent);
                if (!status) {
                    updateFailedForSome = true;
                }
            } catch (IllegalArgumentException exception) {
                log.error(exception.getMessage() + " for dependent : " + dependent);
                skippedSome = true;
            }
        }

        if (skippedSome) {
            return mkResponse(
                    new Response(true, SUCCESS_RESPONSE_CODE,
                            !updateFailedForSome ? "Skipped some dependents update because they missing dependent ID" : "Skipped some dependents update because they missing/having wrong dependent ID",
                            new EmptyJsonBody()), HttpStatus.OK.value());
        }

        return mkResponse(
                new Response(true, SUCCESS_RESPONSE_CODE, !updateFailedForSome ? "": "Skipped some dependents update because they having wrong dependent ID",
                        new EmptyJsonBody()), HttpStatus.OK.value());
    }

    @Override
    public Map.Entry<Response, Integer> deleteDependents(String enrolleeId, List<String> dependentIds) {
        if (enrolleeId == null || enrolleeId.isEmpty()) {
            return mkResponse(new Response(false, ERROR_RESPONSE_CODE, "Enrollee ID can't be null", new EmptyJsonBody()), HttpStatus.BAD_REQUEST.value());
        }

        for (String dependentId : dependentIds) {
            try {
                checkArgument(dependentId != null, "Dependent ID is null");
                enrolleeDepDao.deleteRecord(dependentId);
            } catch (IllegalArgumentException exception) {
                log.error(exception.getMessage());
            }
        }

        Enrollee enrollee = enrolleeDao.fetchRecord(enrolleeId);
        if (enrollee == null) {
            return mkResponse(
                    new Response(false, ERROR_RESPONSE_CODE,
                            "Enrollee doesn't exists for this ID",
                            new EmptyJsonBody()), HttpStatus.NOT_FOUND.value());
        }
        enrollee.getDependents().removeAll(dependentIds);
        enrolleeDao.saveRecord(enrollee);

        return mkResponse(
                new Response(true, SUCCESS_RESPONSE_CODE,
                        "",
                        new EmptyJsonBody()), HttpStatus.OK.value());
    }
}
