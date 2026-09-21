package com.axis.utils;

import io.restassured.response.Response;

public final class ApiLogUtils {
    private ApiLogUtils() {}

    public static String requestAndResponse(Response response) {
        return "HTTP Status: " + response.statusCode()
                + "\nResponse:\n" + response.asPrettyString();
    }
}
