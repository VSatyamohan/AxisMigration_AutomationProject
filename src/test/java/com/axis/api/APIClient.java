package com.axis.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class APIClient {
    public Response findCustomer(String payload) {
        return given()
                .spec(RequestSpecFactory.defaultSpec())
                .body(payload)
                .when()
                .post("/findCustomer");
    }
}
