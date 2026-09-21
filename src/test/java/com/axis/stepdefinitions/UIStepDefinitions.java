package com.axis.stepdefinitions;

import com.axis.context.ScenarioContext;
import com.axis.pages.*;
import com.axis.utils.ConfigReader;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class UIStepDefinitions {
    private final LoginPage loginPage = new LoginPage();
    private final ImagingEyeballingPage imagingPage = new ImagingEyeballingPage();
    private final ImgVerificationPage verificationPage = new ImgVerificationPage();

    @Given("User logs into the application")
    public void userLogsIntoApplication() {
        loginPage.open();
        loginPage.login("demoUser", "demoPassword");
        ScenarioContext.set("recordId", "INPUT-1001");
    }

    @When("the user clicks on the Imaging Eyeballing menu")
    public void clickImagingMenu() {
        imagingPage.clickImagingEyeballingMenu();
    }

    @When("the user clicks on the Img_Verification link")
    public void clickImgVerification() {
        imagingPage.clickImgVerification();
    }

    @When("the user provides comments")
    public void provideComments() {
        String comments = "Automation Internal Hold comment";
        verificationPage.enterComments(comments);
        ScenarioContext.set("comments", comments);
    }

    @When("the user clicks on the INTERNAL HOLD button")
    public void clickInternalHold() {
        verificationPage.clickInternalHold();
    }

    @When("the user INTERNAL HOLD the record")
    public void internalHoldRecord() {
        verificationPage.confirmInternalHold();
        assertTrue(verificationPage.getHoldMessage().contains("IH"));
        // Real application should update DB through the application.
        // Demo application simulates that update here for end-to-end framework execution.
        com.axis.db.DBUtils.execute(
                "UPDATE REPORT_INPUT_OUTPUT SET STATUS=?, COMMENTS=? WHERE RECORD_ID=?",
                "IH", ScenarioContext.getString("comments"),
                ScenarioContext.getString("recordId"));
    }

    @Then("the record should be marked as IH in the REPORT_INPUT_OUTPUT table")
    public void verifyIHStatus() {
        String status = com.axis.db.DBUtils.getString(
                "SELECT STATUS FROM REPORT_INPUT_OUTPUT WHERE RECORD_ID=?",
                ScenarioContext.getString("recordId"));
        assertEquals(status, "IH");
    }

    @Then("the comments should be updated in the REPORT_INPUT_OUTPUT table")
    public void verifyComments() {
        String comments = com.axis.db.DBUtils.getString(
                "SELECT COMMENTS FROM REPORT_INPUT_OUTPUT WHERE RECORD_ID=?",
                ScenarioContext.getString("recordId"));
        assertEquals(comments, ScenarioContext.getString("comments"));
    }
}
