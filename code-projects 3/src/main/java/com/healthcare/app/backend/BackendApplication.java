package com.healthcare.app.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
TODO 1. Set standard Error message for not existing methods or 405
     2. configure mongodb
     3. edge error handling in complete flow
     4. remove all dependents flag in API
     5. change status code int value to appropriate HTTPStatus values
     6. return incorrect id list if time permits, in APIs
 */
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

}
