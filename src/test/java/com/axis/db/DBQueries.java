package com.axis.db;

import com.axis.context.ScenarioContext;

public final class DBQueries {
    private DBQueries() {}

    public static String getStatusByRecordId(String recordId) {
        return DBUtils.getString(
                "SELECT STATUS FROM REPORT_INPUT_OUTPUT WHERE RECORD_ID = ?",
                recordId);
    }

    public static String getCommentsByRecordId(String recordId) {
        return DBUtils.getString(
                "SELECT COMMENTS FROM REPORT_INPUT_OUTPUT WHERE RECORD_ID = ?",
                recordId);
    }

    public static String getRequestStatusByUCIC(String ucic) {
        return DBUtils.getString(
                "SELECT REQUEST_STATUS FROM FIND_CUSTOMER_REQUEST WHERE UCIC = ?",
                ucic);
    }

    public static int getMatchCountByUCIC(String ucic) {
        return DBUtils.getInt(
                "SELECT MATCH_COUNT FROM FIND_CUSTOMER_REQUEST WHERE UCIC = ?",
                ucic);
    }

    public static String getUCICByRequestId(String requestId) {
        return DBUtils.getString(
                "SELECT UCIC FROM FIND_CUSTOMER_REQUEST WHERE REQUEST_ID = ?",
                requestId);
    }
}
