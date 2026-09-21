package com.axis.context;

import java.util.HashMap;
import java.util.Map;

public final class TestDataContext {
    private static final ThreadLocal<Map<String, String>> DATA =
            ThreadLocal.withInitial(HashMap::new);

    private TestDataContext() {}

    public static void set(String key, String value) {
        DATA.get().put(key, value);
    }

    public static String get(String key) {
        return DATA.get().get(key);
    }

    public static Map<String, String> getAll() {
        return new HashMap<>(DATA.get());
    }

    public static void clear() {
        DATA.remove();
    }
}
