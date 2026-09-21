package com.axis.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtils {
    private WaitUtils() {}

    private static WebDriverWait wait() {
        return new WebDriverWait(DriverFactory.getDriver(),
                Duration.ofSeconds(ConfigReader.getInt("explicitWait")));
    }

    public static WebElement visible(By locator) {
        return wait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static WebElement clickable(By locator) {
        return wait().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void click(By locator) {
        clickable(locator).click();
    }

    public static void type(By locator, String value) {
        WebElement element = visible(locator);
        element.clear();
        element.sendKeys(value);
    }
}
