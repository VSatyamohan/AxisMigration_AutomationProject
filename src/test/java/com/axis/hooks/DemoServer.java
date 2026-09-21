package com.axis.hooks;

import com.axis.db.DBUtils;
import com.axis.utils.ConfigReader;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.Executors;

public final class DemoServer {
    private static HttpServer server;

    private DemoServer() {}

    public static void start() {
        if (server != null) return;
        try {
            DBUtils.connect();
            DBUtils.execute("""
                CREATE TABLE IF NOT EXISTS REPORT_INPUT_OUTPUT(
                    RECORD_ID VARCHAR(50) PRIMARY KEY,
                    STATUS VARCHAR(20),
                    COMMENTS VARCHAR(500)
                )
            """);
            DBUtils.execute("""
                MERGE INTO REPORT_INPUT_OUTPUT KEY(RECORD_ID)
                VALUES('INPUT-1001','PENDING','')
            """);

            DBUtils.execute("""
                CREATE TABLE IF NOT EXISTS FIND_CUSTOMER_REQUEST(
                    REQUEST_ID VARCHAR(50) PRIMARY KEY,
                    UCIC VARCHAR(50),
                    REQUEST_STATUS VARCHAR(30),
                    MATCH_COUNT INT
                )
            """);

            server = HttpServer.create(new InetSocketAddress(8085), 0);

            server.createContext("/", exchange -> {
                Path path = Path.of("src/test/resources/demo-app/index.html");
                byte[] data = Files.readAllBytes(path);
                exchange.getResponseHeaders().add("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, data.length);
                try (OutputStream out = exchange.getResponseBody()) {
                    out.write(data);
                }
            });

            server.createContext("/api/findCustomer", DemoServer::findCustomer);

            server.setExecutor(Executors.newCachedThreadPool());
            server.start();
        } catch (Exception e) {
            throw new RuntimeException("Unable to start demo server", e);
        }
    }

    private static void findCustomer(HttpExchange exchange) throws IOException {
        String requestId = UUID.randomUUID().toString();
        String ucic = "UCIC-" + (100000 + (int)(Math.random() * 900000));
        int matchCount = 1;

        DBUtils.execute(
                "INSERT INTO FIND_CUSTOMER_REQUEST(REQUEST_ID,UCIC,REQUEST_STATUS,MATCH_COUNT) VALUES(?,?,?,?)",
                requestId, ucic, "PROCESSED", matchCount);

        JSONObject response = new JSONObject();
        response.put("status", "PROCESSED");
        response.put("requestId", requestId);
        response.put("ucic", ucic);
        response.put("matchCount", matchCount);

        byte[] data = response.toString().getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json");
        exchange.sendResponseHeaders(200, data.length);
        try (OutputStream out = exchange.getResponseBody()) {
            out.write(data);
        }
    }

    public static void stop() {
        if (server != null) {
            server.stop(0);
            server = null;
        }
    }
}
