package com.axis.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features/ui",
        glue = {"com.axis.hooks", "com.axis.stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/ui.html",
                "json:target/cucumber-reports/ui.json",
                "rerun:target/failed-ui.txt"
        },
        monochrome = true,
        tags = "@UI"
)
public class UIRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
