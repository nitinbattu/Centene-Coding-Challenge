package com.healthcare.app.backend.util;

public class Constants {
    public static final String CURRENT_API_VERSION = "v1";

    // mongo consts
    public static final String MONGO_DB_NAME = "users";
    public static final String MONGO_ENROLLEE_COLLECTION = "enrollee";
    public static final String MONGO_ENROLLEE_DEPENDENT_COLLECTION = "dependent";

    // all response codes has to be starting from 1 and following actual HTTP status range like 4xx, 2xx etc.
    public static final long SUCCESS_RESPONSE_CODE = 1200;
    public static final long ERROR_RESPONSE_CODE = 1400;

}
