package com.axis.db;

import com.axis.utils.ConfigReader;

import java.sql.*;

public final class DBUtils {
    private static final ThreadLocal<Connection> CONNECTION = new ThreadLocal<>();

    private DBUtils() {}

    public static void connect() {
        try {
            Class.forName(ConfigReader.get("db.driver"));
            CONNECTION.set(DriverManager.getConnection(
                    ConfigReader.get("db.url"),
                    ConfigReader.get("db.username"),
                    ConfigReader.get("db.password")));
        } catch (Exception e) {
            throw new RuntimeException("DB connection failed", e);
        }
    }

    public static Connection getConnection() {
        if (CONNECTION.get() == null) connect();
        return CONNECTION.get();
    }

    public static String getString(String sql, Object... params) {
        try (PreparedStatement ps = prepare(sql, params);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getString(1) : null;
        } catch (SQLException e) {
            throw new RuntimeException("DB query failed: " + sql, e);
        }
    }

    public static int getInt(String sql, Object... params) {
        try (PreparedStatement ps = prepare(sql, params);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt(1) : 0;
        } catch (SQLException e) {
            throw new RuntimeException("DB query failed: " + sql, e);
        }
    }

    public static void execute(String sql, Object... params) {
        try (PreparedStatement ps = prepare(sql, params)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("DB update failed: " + sql, e);
        }
    }

    private static PreparedStatement prepare(String sql, Object... params) throws SQLException {
        PreparedStatement ps = getConnection().prepareStatement(sql);
        for (int i = 0; i < params.length; i++) ps.setObject(i + 1, params[i]);
        return ps;
    }

    public static void close() {
        try {
            if (CONNECTION.get() != null) CONNECTION.get().close();
        } catch (SQLException ignored) {
        } finally {
            CONNECTION.remove();
        }
    }
}
