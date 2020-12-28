package com.healthcare.app.backend;

import com.healthcare.app.backend.model.Dependent;
import com.healthcare.app.backend.model.Enrollee;

import java.util.Calendar;
import java.util.Date;

public class Utils {
    public static String formatEndpointPath(String host, int port, String[] paths){
        StringBuilder builder = new StringBuilder();
        builder.append("http://");
        builder.append(host).append(":").append(port);
        for(String path : paths) {
            builder.append("/").append(path);
        }
        return builder.toString();
    }

    public static Enrollee mkEnrollee(){
        Enrollee enrollee = new Enrollee();
        enrollee.setName("Testing User");
        enrollee.setActivationStatus(true);
        enrollee.setDob(new Date(1990, Calendar.OCTOBER, 10));
        return enrollee;
    }

    public static Dependent mkDependent(){
        Dependent dependent = new Dependent();
        dependent.setName("Testing Dependent");
        dependent.setDob(new Date(1990, Calendar.OCTOBER, 10));
        return dependent;
    }
}
