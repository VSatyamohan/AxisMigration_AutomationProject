package com.axis.api;

import com.axis.utils.JsonUtils;
import io.restassured.response.Response;

import static org.testng.Assert.*;

public final class APIResponseValidator {
    private APIResponseValidator() {}

    public static void validateProcessed(Response response) {
        assertEquals(response.statusCode(), 200, "Unexpected HTTP status");
        assertEquals(JsonUtils.getString(response.asString(), "status"),
                "PROCESSED", "Business status is not PROCESSED");
    }

    public static String validateAndGetUCIC(Response response) {
        String ucic = JsonUtils.getString(response.asString(), "ucic");
        assertNotNull(ucic, "UCIC is missing");
        assertFalse(ucic.isBlank(), "UCIC is blank");
        return ucic;
    }

    public static int validateAndGetMatchCount(Response response) {
        Integer count = JsonUtils.getInt(response.asString(), "matchCount");
        assertNotNull(count, "matchCount is missing");
        assertTrue(count >= 0, "matchCount cannot be negative");
        return count;
    }
}
