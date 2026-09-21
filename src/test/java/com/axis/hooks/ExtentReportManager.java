package com.axis.hooks;

import com.axis.utils.ConfigReader;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {
    private static ExtentReports extent;

    private ExtentReportManager() {}

    public static synchronized ExtentReports getExtent() {
        if (extent == null) {
            try {
                Files.createDirectories(Path.of(ConfigReader.get("report.dir")));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            ExtentSparkReporter reporter = new ExtentSparkReporter(
                    ConfigReader.get("report.dir") + "/ExtentReport.html");
            reporter.config().setDocumentTitle("Axis Automation Report");
            reporter.config().setReportName("Cucumber UI + API Automation");
            extent = new ExtentReports();
            extent.attachReporter(reporter);
        }
        return extent;
    }

    public static synchronized void flush() {
        if (extent != null) extent.flush();
    }
}
