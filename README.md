# Axis Cucumber BDD Automation Framework

This is a **self-contained dummy framework** for interview practice.

It contains:

- Selenium + Java UI automation
- Cucumber BDD
- TestNG
- Page Object Model
- LoginPage
- ImagingEyeballingPage
- ImgVerificationPage
- Rest Assured API automation
- APIClient
- APIResponseValidator
- RequestSpecification factory
- JSON payload builder
- JSON utility
- H2 database validation
- DBUtils
- DBQueries
- ThreadLocal ScenarioContext
- ThreadLocal TestDataContext
- Screenshot capture on UI failure
- Cucumber scenario attachment
- Extent report
- Cucumber HTML/JSON report
- Parallel execution
- RetryAnalyzer
- Cucumber rerun file
- ExcelReader
- ChromeOptions: headless, incognito, disable notifications
- Maven/TestNG execution

## Package structure

com.axis.hooks
com.axis.pages
com.axis.stepdefinitions
com.axis.runners
com.axis.api
com.axis.utils
com.axis.db
com.axis.context
com.axis.retry

## Run directly

Prerequisites:

1. Java 17+
2. Maven 3.8+
3. Google Chrome
4. Eclipse with Maven support

From project root:

    mvn clean test

Or:

    mvn clean test -Dtest=UIRunner
    mvn clean test -Dtest=APIRunner

Selenium Manager is used by Selenium 4 to obtain the Chrome driver when required.

## What happens during the demo

The framework starts a small local HTTP server on port 8085.

The UI scenario uses:

    http://localhost:8085

The API scenario uses:

    http://localhost:8085/api/findCustomer

The H2 database is initialized in memory.

This allows the project to demonstrate a complete end-to-end flow without needing your actual application.

## Important production changes

For your real Axis/project application, change:

1. config/config.properties
   - baseUrl
   - apiBaseUrl
   - DB URL/user/password/driver
   - authentication/token

2. Page Object locators
   - LoginPage
   - ImagingEyeballingPage
   - ImgVerificationPage

3. APIClient
   - endpoint
   - headers
   - authentication
   - request method

4. JsonPayloadBuilder
   - actual request JSON

5. APIResponseValidator
   - actual response paths and business rules

6. DBQueries
   - actual table/column names and queries

7. UI step definition
   - actual record selection / record ID logic

## Screenshot on failure

Flow:

Scenario step fails
 -> @AfterStep
 -> scenario.isFailed()
 -> TakesScreenshot
 -> byte[]
 -> scenario.attach(...)
 -> Cucumber report

The saved PNG is also written under:

    target/screenshots

## Reporting

Cucumber:

    target/cucumber-reports/

Extent:

    target/extent-report/ExtentReport.html

## Parallel execution

UIRunner and APIRunner use:

    @DataProvider(parallel = true)

WebDriver is stored in:

    ThreadLocal<WebDriver>

ScenarioContext and TestDataContext are also ThreadLocal.

This prevents parallel scenarios from sharing driver/runtime data.

## Retry strategy

RetryAnalyzer demonstrates TestNG retry.

For Cucumber, failed scenarios are additionally written to:

    target/failed-ui.txt
    target/failed-api.txt
    target/failed-rerun.txt

For real projects, retry should be limited to transient failures such as network instability or environment issues. Do not retry genuine functional failures blindly.

## Senior interview explanation

If asked "How is your framework designed?"

Say:

"I use a layered Cucumber BDD framework. Feature files contain business-readable scenarios. Step definitions act as the glue layer. UI actions are isolated in Page Object classes. API operations are separated into API client and response validator classes. Runtime data such as UCIC, response and record IDs are maintained in a ThreadLocal ScenarioContext for parallel execution. Database access is centralized in DBUtils and DBQueries. Hooks handle driver lifecycle, screenshot capture and reporting. TestNG DataProvider enables parallel scenario execution. For failures, screenshots are captured in the Cucumber @AfterStep hook and attached to the Scenario object, which makes them available in the report. API responses are validated using status-code, JSON-path and business validations, and important response values are stored in ScenarioContext for subsequent DB validation."

## Important note

This project deliberately uses dummy application details so that it can run as a demonstration framework. It is not connected to your real application or production database.

Do not commit real credentials into Git. Use environment variables, Jenkins credentials, or a secure secrets mechanism in the real project.
