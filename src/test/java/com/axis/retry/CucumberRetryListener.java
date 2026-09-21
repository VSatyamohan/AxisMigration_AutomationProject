package com.axis.retry;

import org.testng.*;
import org.testng.annotations.ITestAnnotation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/*
 * Cucumber scenarios are not normal @Test methods, so attaching
 * IRetryAnalyzer directly to every generated Cucumber test is not reliable.
 * This listener demonstrates controlled TestNG retry handling for Cucumber
 * generated methods. For production Cucumber suites, rerun failed scenarios
 * through Cucumber's rerun plugin is also recommended.
 */
public class CucumberRetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass,
                          java.lang.reflect.Constructor testConstructor,
                          java.lang.reflect.Method testMethod) {
        if (testMethod != null && testMethod.getName().contains("runScenario")) {
            annotation.setRetryAnalyzer(RetryAnalyzer.class);
        }
    }
}
