package com.axis.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/api",
        glue = {"com.axis.hooks", "com.axis.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api.html",
                "json:target/cucumber-reports/api.json",
                "rerun:target/failed-api.txt"
        },
        monochrome = true,
        tags = "@API"
)
public class APIRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
