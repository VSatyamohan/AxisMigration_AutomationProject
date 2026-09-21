package com.axis.api;

import com.axis.utils.ConfigReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public final class RequestSpecFactory {
    private RequestSpecFactory() {}

    public static RequestSpecification defaultSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.get("apiBaseUrl"))
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + ConfigReader.get("api.token"))
                .addHeader("Accept", "application/json")
                .build();
    }
}
