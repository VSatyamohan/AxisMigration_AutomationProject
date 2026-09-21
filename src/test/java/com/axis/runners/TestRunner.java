package com.axis.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.axis.hooks", "com.axis.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/combined.html",
                "json:target/cucumber-reports/combined.json",
                "rerun:target/failed-rerun.txt"
        },
        monochrome = true,
        tags = "@UI or @API"
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
