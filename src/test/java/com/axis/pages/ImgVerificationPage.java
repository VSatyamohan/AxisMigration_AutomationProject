package com.axis.pages;

import com.axis.utils.WaitUtils;
import org.openqa.selenium.By;

public class ImgVerificationPage {
    private final By comments = By.id("comments");
    private final By internalHoldButton = By.id("internalHoldButton");
    private final By confirmHoldButton = By.id("confirmHoldButton");
    private final By holdMessage = By.id("holdMessage");

    public void enterComments(String value) {
        WaitUtils.type(comments, value);
    }

    public void clickInternalHold() {
        WaitUtils.click(internalHoldButton);
    }

    public void confirmInternalHold() {
        WaitUtils.click(confirmHoldButton);
    }

    public String getHoldMessage() {
        return WaitUtils.visible(holdMessage).getText();
    }
}
