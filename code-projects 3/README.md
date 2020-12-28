# Healthcare Tracking App Microservice

## Prerequisite and Tech stack
1. Java 8
2. [MongoDB](https://docs.mongodb.com/manual/administration/install-community/)
3. [Postman](https://www.postman.com/downloads/) (API Documentation)

## Tech Stack
1. Java - language of code
2. Sprint Boot - Rest API Framework
3. Gradle - build tool
4. MongoDB - database

## Running Application
We are utilizing gradle for building our application so here are few commands to follow for building and running project

Set `pwd`(current directory in terminal) to root of project 
1. Build application 
    ```
   ./gradlew clean build
   ```
2. Running application
    ```
   ./gradlew bootRun
   ```
3. Running tests
    ```
   ./gradlew clean test
   ```
   
## Configurations
Application properties such as `APPLICATION PORT`, `DATABASE HOST` etc are defined in 
`src/main/resources/application.properties`. You can modify or add other according to use case.

## Codebase
Application start point is `BackendApplication.class` which starts the Spring boot application

Codebase follows the standard design of MVC (Controller, Service, Repository) pattern and utilises useful features of Spring boot
## API Documentation
Documentation of Rest APIs is in `docs.postman_collection.json` Postman file. Simply import this file in Postman and try using Rest APIs.


## Testing Setup
We are using embedded mongodb for testing purpose so don't need to worry about setting multiple environment for testing and development.

For this, we are using `de.flapdoodle.embed.mongo` package which embed mongodb for testing purpose and clean all at the end of testing process.

Configuration/Properties of testing environment are defined in `src/main/resources/application-integrationtest.properties`.
