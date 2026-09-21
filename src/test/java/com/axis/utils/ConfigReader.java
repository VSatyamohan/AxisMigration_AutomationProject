package com.axis.utils;

import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
                .getResourceAsStream("config/config.properties")) {
            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }
            PROPERTIES.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Unable to load config.properties", e);
        }
    }

    private ConfigReader() {}

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Missing configuration key: " + key);
        }
        return value.trim();
    }

    public static boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }

    public static int getInt(String key) {
        return Integer.parseInt(get(key));
    }
}
