package com.healthcare.app.backend.util;

import com.google.common.collect.Maps;
import com.healthcare.app.backend.model.Response;

import java.util.Map;

public class CommonUtils {
    public static Map.Entry<Response, Integer> mkResponse(Response response, int statusCode){
        return Maps.immutableEntry(response, statusCode);
    }
}
