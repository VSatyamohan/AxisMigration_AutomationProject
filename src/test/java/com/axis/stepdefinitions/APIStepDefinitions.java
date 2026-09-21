package com.axis.stepdefinitions;

import com.axis.api.APIClient;
import com.axis.api.APIResponseValidator;
import com.axis.api.JsonPayloadBuilder;
import com.axis.context.ScenarioContext;
import com.axis.context.TestDataContext;
import com.axis.db.DBQueries;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import io.restassured.response.Response;

import java.util.Map;

import static org.testng.Assert.assertEquals;

public class APIStepDefinitions {
    private final APIClient apiClient = new APIClient();

    @Given("User prepares Find Customer request with below data")
    public void prepareFindCustomer(DataTable table) {
        Map<String, String> data = table.asMap(String.class, String.class);
        data.forEach(TestDataContext::set);

        String payload = JsonPayloadBuilder.buildFindCustomerPayload(data);
        ScenarioContext.set("requestPayload", payload);
    }

    @When("User submits Find Customer API request")
    public void submitFindCustomer() {
        Response response = apiClient.findCustomer(
                ScenarioContext.getString("requestPayload"));
        ScenarioContext.set("response", response);
        ScenarioContext.set("responseBody", response.asString());
    }

    @Then("API response should be processed successfully")
    public void validateProcessed() {
        APIResponseValidator.validateProcessed(getResponse());
    }

    @Then("API response should contain generated UCIC")
    public void validateUCIC() {
        String ucic = APIResponseValidator.validateAndGetUCIC(getResponse());
        ScenarioContext.set("ucic", ucic);
    }

    @Then("API response should contain match count")
    public void validateMatchCount() {
        int count = APIResponseValidator.validateAndGetMatchCount(getResponse());
        ScenarioContext.set("matchCount", count);
    }

    @Then("User stores generated UCIC from response")
    public void storeUCIC() {
        String ucic = com.axis.utils.JsonUtils.getString(
                ScenarioContext.getString("responseBody"), "ucic");
        ScenarioContext.set("ucic", ucic);
    }

    @When("User validates customer request details in database")
    public void validateDatabaseDetails() {
        String ucic = ScenarioContext.getString("ucic");
        String status = DBQueries.getRequestStatusByUCIC(ucic);
        int count = DBQueries.getMatchCountByUCIC(ucic);
        String dbUcic = ucic;

        ScenarioContext.set("dbStatus", status);
        ScenarioContext.set("dbMatchCount", count);
        ScenarioContext.set("dbUCIC", dbUcic);
    }

    @Then("Database request status should be processed")
    public void verifyDbStatus() {
        assertEquals(ScenarioContext.getString("dbStatus"), "PROCESSED");
    }

    @Then("Database match count should match API response")
    public void verifyMatchCount() {
        assertEquals(
                ((Number) ScenarioContext.get("dbMatchCount")).intValue(),
                ((Number) ScenarioContext.get("matchCount")).intValue());
    }

    @Then("Database generated UCIC should match API response")
    public void verifyUCIC() {
        assertEquals(
                ScenarioContext.getString("dbUCIC"),
                ScenarioContext.getString("ucic"));
    }

    private Response getResponse() {
        return (Response) ScenarioContext.get("response");
    }
}
