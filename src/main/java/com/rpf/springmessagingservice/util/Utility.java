package com.rpf.springmessagingservice.util;

import com.google.gson.Gson;

public class Utility {

    public static String convertObjectAsJson(Object object) {
        return new Gson().toJson(object);
    }
}
