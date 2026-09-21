package com.axis.pages;

import com.axis.utils.WaitUtils;
import org.openqa.selenium.By;

public class ImagingEyeballingPage {
    private final By imagingMenu = By.id("imagingMenu");
    private final By imgVerification = By.id("imgVerificationLink");

    public void clickImagingEyeballingMenu() {
        WaitUtils.click(imagingMenu);
    }

    public void clickImgVerification() {
        WaitUtils.click(imgVerification);
    }
}
