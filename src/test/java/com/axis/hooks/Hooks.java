package com.axis.hooks;

import com.axis.context.ScenarioContext;
import com.axis.context.TestDataContext;
import com.axis.db.DBUtils;
import com.axis.utils.DriverFactory;
import com.axis.utils.ScreenshotUtils;
import com.aventstack.extentreports.ExtentTest;
import io.cucumber.java.*;
import io.cucumber.plugin.event.PickleStepTestStep;

public class Hooks {
    private static final ThreadLocal<ExtentTest> EXTENT_TEST = new ThreadLocal<>();

    @BeforeAll
    public static void beforeAll() {
        DemoServer.start();
        ExtentReportManager.getExtent();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        ScenarioContext.clear();
        TestDataContext.clear();

        String feature = scenario.getUri().toString();
        EXTENT_TEST.set(ExtentReportManager.getExtent()
                .createTest(scenario.getName())
                .assignCategory(feature.contains("/ui/") ? "UI" : "API"));

        if (scenario.getSourceTagNames().contains("@UI")) {
            DriverFactory.initDriver();
        }
        if (scenario.getSourceTagNames().contains("@API")) {
            DBUtils.connect();
        }
    }

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (scenario.isFailed() && scenario.getSourceTagNames().contains("@UI")) {
            try {
                byte[] screenshot = ScreenshotUtils.captureBytes();
                scenario.attach(screenshot, "image/png", "Failure Screenshot");
                EXTENT_TEST.get().fail("Step failed - screenshot attached");
            } catch (Exception e) {
                EXTENT_TEST.get().fail("Unable to capture screenshot: " + e.getMessage());
            }
        }
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            EXTENT_TEST.get().fail("Scenario FAILED");
        } else {
            EXTENT_TEST.get().pass("Scenario PASSED");
        }

        if (scenario.getSourceTagNames().contains("@UI")) {
            DriverFactory.quitDriver();
        }
        DBUtils.close();
        ScenarioContext.clear();
        TestDataContext.clear();
        EXTENT_TEST.remove();
    }

    @AfterAll
    public static void afterAll() {
        ExtentReportManager.flush();
        DemoServer.stop();
    }
}
