package com.healthcare.app.backend.service;

import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.Response;

import java.util.Map;

public interface EnrollmentService {
    Map.Entry<Response, Integer> addEnrollee(Enrollee newEnrollee);
    Map.Entry<Response, Integer> updateEnrollee(String enrolleeId, Enrollee updates);
    Map.Entry<Response, Integer> deleteEnrollee(String enrolleeId);
}
