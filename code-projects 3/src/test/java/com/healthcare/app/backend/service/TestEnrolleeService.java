package com.healthcare.app.backend.service;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.impl.EnrollmentServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Map;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class TestEnrolleeService {

    @Mock
    private DataOperations<Enrollee, String> enrolleeDao;

    @Mock
    DataOperations<Dependent, String> enrolleeDepDao;


    @InjectMocks
    private final EnrollmentService enrolleeService = new EnrollmentServiceImpl(enrolleeDao, enrolleeDepDao);

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_addEnrollee_notEnoughArguments(){
        Enrollee enrollee = new Enrollee();
        enrollee.setName("Testing Name");
        Map.Entry<Response, Integer> response = enrolleeService.addEnrollee(enrollee);
        assertFalse(response.getKey().getStatus());
    }

    @Test
    public void test_updateEnrollee_notEnoughArguments(){
        Enrollee enrollee = new Enrollee();
        enrollee.setName("Testing Name");
        Map.Entry<Response, Integer> response = enrolleeService.updateEnrollee("", enrollee);
        assertFalse(response.getKey().getStatus());
    }

    @Test
    public void test_deleteEnrollee_nullOrEmptyId(){
        Map.Entry<Response, Integer> response = enrolleeService.deleteEnrollee("");
        assertFalse(response.getKey().getStatus());

        response = enrolleeService.deleteEnrollee(null);
        assertFalse(response.getKey().getStatus());
    }


}
