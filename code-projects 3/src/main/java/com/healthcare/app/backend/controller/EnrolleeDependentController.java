package com.healthcare.app.backend.controller;

import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Response;
import com.healthcare.app.backend.service.EnrolleeDependentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.healthcare.app.backend.util.Constants.CURRENT_API_VERSION;

@RestController
@RequestMapping(value = "/" + CURRENT_API_VERSION + "/dependent")
@Slf4j
public class EnrolleeDependentController {

    @Autowired
    EnrolleeDependentService dependentService;

    @PostMapping(value = "/{enrollee_id}")
    public ResponseEntity<Response> addDependents(@PathVariable String enrollee_id, @RequestBody List<Dependent> dependentDetails){
        Map.Entry<Response, Integer> response = dependentService.addDependents(enrollee_id, dependentDetails);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }

    @PutMapping(value = "/update")
    public ResponseEntity<Response> updateDependents( @RequestBody List<Dependent> dependentDetails){
        Map.Entry<Response, Integer> response = dependentService.updateDependents(dependentDetails);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }

    @PutMapping(value = "/delete/{enrollee_id}")
    public ResponseEntity<Response> deleteDependents(@PathVariable String enrollee_id, @RequestBody List<String> dependentDetails){
        Map.Entry<Response, Integer> response = dependentService.deleteDependents(enrollee_id, dependentDetails);
        return new ResponseEntity<>(response.getKey(), HttpStatus.valueOf(response.getValue()));
    }

}
