package com.healthcare.app.backend;

import static com.healthcare.app.backend.TestConstants.*;
import static com.healthcare.app.backend.Utils.*;
import static org.junit.Assert.*;

import com.healthcare.app.backend.controller.EnrollmentController;
import com.healthcare.app.backend.dao.DataOperations;
import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.IDField;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.EnrollmentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
Integration Test for all the APIs for enrollee and it's dependents using RestTemplate
 */
@RunWith(SpringRunner.class)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BackendApplicationTests {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private DataOperations<Enrollee, String> enrolleeDao;

    @Autowired
    private DataOperations<Dependent, String> enrolleeDepDao;

    @Test
    void contextLoads() {
    }

    @Test
    void test_createEnrollee(){
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), mkEnrollee(), Response.class);
        assertTrue(response.getStatus());
    }

    @Test
    void test_updateEnrollee() throws ParseException {
        Enrollee enrollee = mkEnrollee();
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), enrollee, Response.class);
        assertTrue(response.getStatus());
        String enrolleeId = (String)((LinkedHashMap) response.getExtraData()).get("id");

        Enrollee updates = new Enrollee();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));

        updates.setDob(dateFormat.parse("1999-10-13"));
        updates.setPhoneNumber("666-444-9564");
        updates.setName("Testing User Blob");

        restTemplate.put(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT, enrolleeId}), updates);

        enrollee = enrolleeDao.fetchRecord(enrolleeId);
        assertEquals(updates.getDob(), enrollee.getDob());
        assertEquals(updates.getName(), enrollee.getName());
        assertEquals(updates.getPhoneNumber(), enrollee.getPhoneNumber());
    }

    @Test
    void test_deleteEnrollee() {
        Enrollee enrollee = mkEnrollee();
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), enrollee, Response.class);
        assertTrue(response.getStatus());
        String enrolleeId = (String)((LinkedHashMap) response.getExtraData()).get("id");

        restTemplate.delete(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT, enrolleeId}));
        enrollee = enrolleeDao.fetchRecord(enrolleeId);
        assertNull(enrollee);
    }

    @Test
    void test_createDependents(){
        Enrollee enrollee = mkEnrollee();
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), enrollee, Response.class);
        assertTrue(response.getStatus());

        String enrolleeId = (String)((LinkedHashMap) response.getExtraData()).get("id");

        List<Dependent> dependents = new ArrayList<>();
        Dependent dependent = mkDependent();
        dependents.add(dependent);

        Response updateResponse = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, DEPENDENT_ENDPOINT, enrolleeId}), dependents, Response.class);
        assertTrue(updateResponse.getStatus());
    }

    @Test
    void test_updateDependents(){
        Enrollee enrollee = mkEnrollee();
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), enrollee, Response.class);
        assertTrue(response.getStatus());

        String enrolleeId = (String)((LinkedHashMap) response.getExtraData()).get("id");

        List<Dependent> dependents = new ArrayList<>();
        Dependent dependent = mkDependent();
        dependents.add(dependent);

        Response updateResponse = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, DEPENDENT_ENDPOINT, enrolleeId}), dependents, Response.class);
        assertTrue(updateResponse.getStatus());
        List<LinkedHashMap> updateDependent = (List<LinkedHashMap>) updateResponse.getExtraData();

        LinkedHashMap hashMap = updateDependent.get(0);

        Dependent dependent1 = dependents.get(0);
        dependent1.setName("Testing Dependent Blob 2");
        dependent1.setId((String) hashMap.get("id"));

        restTemplate.put(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, DEPENDENT_ENDPOINT, "update"}), dependents, Response.class);

        Dependent inDependent = enrolleeDepDao.fetchRecord(dependent1.getId());
        assertEquals(dependent1.getName(), inDependent.getName());
    }

    @Test
    void test_deleteDependents(){
        Enrollee enrollee = mkEnrollee();
        Response response = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, ENROLLEE_ENDPOINT}), enrollee, Response.class);
        assertTrue(response.getStatus());

        String enrolleeId = (String)((LinkedHashMap) response.getExtraData()).get("id");

        List<Dependent> dependents = new ArrayList<>();
        Dependent dependent = mkDependent();
        dependents.add(dependent);

        Response updateResponse = restTemplate.postForObject(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, DEPENDENT_ENDPOINT, enrolleeId}), dependents, Response.class);
        assertTrue(updateResponse.getStatus());
        List<LinkedHashMap> updateDependent = (List<LinkedHashMap>) updateResponse.getExtraData();

        LinkedHashMap hashMap = updateDependent.get(0);
        List<String> depIds = new ArrayList<>();
        depIds.add((String) hashMap.get("id"));

        restTemplate.put(formatEndpointPath(HOST, randomServerPort, new String[]{API_VERSION, DEPENDENT_ENDPOINT, DELETE_PATH, enrolleeId}), depIds, Response.class);
        assertNull(enrolleeDepDao.fetchRecord(depIds.get(0)));
    }
}
