package com.healthcare.app.backend.service;

import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.impl.EnrolleeDependentServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.Map;

import static org.junit.Assert.assertFalse;

@RunWith(MockitoJUnitRunner.class)
public class TestEnrolleeDepService {

    @Mock
    private DataOperations<Enrollee, String> enrolleeDao;

    @Mock
    DataOperations<Dependent, String> enrolleeDepDao;

    @InjectMocks
    private final EnrolleeDependentService enrolleeService = new EnrolleeDependentServiceImpl(enrolleeDao, enrolleeDepDao);

    @Before
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void test_addEnrolleeDependent_emptyOrNullId(){
        Map.Entry<Response, Integer> response = enrolleeService.addDependents("", Collections.emptyList());
        assertFalse(response.getKey().getStatus());

        response = enrolleeService.addDependents(null, Collections.emptyList());
        assertFalse(response.getKey().getStatus());
    }

    @Test
    public void test_deleteEnrolleeDependent_emptyOrNullId(){

        Map.Entry<Response, Integer> response = enrolleeService.deleteDependents("", Collections.emptyList());
        assertFalse(response.getKey().getStatus());

        response = enrolleeService.deleteDependents(null, Collections.emptyList());
        assertFalse(response.getKey().getStatus());
    }
}
