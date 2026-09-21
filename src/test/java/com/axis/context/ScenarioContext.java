package com.axis.context;

import java.util.HashMap;
import java.util.Map;

public final class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> DATA =
            ThreadLocal.withInitial(HashMap::new);

    private ScenarioContext() {}

    public static void set(String key, Object value) {
        DATA.get().put(key, value);
    }

    public static Object get(String key) {
        return DATA.get().get(key);
    }

    public static String getString(String key) {
        Object value = get(key);
        return value == null ? null : value.toString();
    }

    public static void clear() {
        DATA.remove();
    }
}
