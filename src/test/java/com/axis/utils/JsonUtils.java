package com.axis.utils;

import io.restassured.path.json.JsonPath;

public final class JsonUtils {
    private JsonUtils() {}

    public static String getString(String json, String path) {
        return new JsonPath(json).getString(path);
    }

    public static Integer getInt(String json, String path) {
        return new JsonPath(json).getInt(path);
    }

    public static boolean hasPath(String json, String path) {
        try {
            return new JsonPath(json).get(path) != null;
        } catch (Exception e) {
            return false;
        }
    }
}
