package com.healthcare.app.backend.controller;

import com.healthcare.app.backend.model.Enrollee;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

import static com.healthcare.app.backend.util.Constants.CURRENT_API_VERSION;

@RestController
@RequestMapping(value = "/" + CURRENT_API_VERSION + "/enrollee")
@Slf4j
public class EnrollmentController {

    @Autowired
    EnrollmentService enrollmentService;

    @PostMapping()
    public ResponseEntity<Response> addEnrollee(@RequestBody Enrollee newEnrollee){
        Map.Entry<Response, Integer> response = enrollmentService.addEnrollee(newEnrollee);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }

    @PutMapping(value = "/{enrollee_id}")
    public ResponseEntity<Response> updateEnrollee(@PathVariable("enrollee_id") String enrolleeId, @RequestBody Enrollee updates){
        Map.Entry<Response, Integer> response = enrollmentService.updateEnrollee(enrolleeId, updates);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }

    @DeleteMapping(value = "/{enrollee_id}")
    public ResponseEntity<Response> deleteEnrollee(@PathVariable("enrollee_id") String enrolleeId){
        Map.Entry<Response, Integer> response = enrollmentService.deleteEnrollee(enrolleeId);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }
}
