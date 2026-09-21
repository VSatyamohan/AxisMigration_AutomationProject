package com.axis.api;

import org.json.JSONObject;

import java.util.Map;

public final class JsonPayloadBuilder {
    private JsonPayloadBuilder() {}

    public static String buildFindCustomerPayload(Map<String, String> data) {
        JSONObject customer = new JSONObject();
        customer.put("customerName", data.getOrDefault("customerName", ""));
        customer.put("mobile", data.getOrDefault("mobile", ""));
        customer.put("email", data.getOrDefault("email", ""));

        JSONObject root = new JSONObject();
        root.put("customer", customer);
        return root.toString();
    }
}
