package com.healthcare.app.backend.service;

import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Response;

import java.util.List;
import java.util.Map;

public interface EnrolleeDependentService {
    Map.Entry<Response, Integer> addDependents(String enrolleeId, List<Dependent> dependents);
    Map.Entry<Response, Integer> updateDependents(List<Dependent> dependents);
    Map.Entry<Response, Integer> deleteDependents(String enrolleeId, List<String> dependents);
}
