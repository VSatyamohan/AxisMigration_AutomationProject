package com.axis.pages;

import com.axis.utils.ConfigReader;
import com.axis.utils.DriverFactory;
import com.axis.utils.WaitUtils;
import org.openqa.selenium.By;

public class LoginPage {
    private final By username = By.id("username");
    private final By password = By.id("password");
    private final By loginButton = By.id("loginBtn");

    public void open() {
        DriverFactory.getDriver().get(ConfigReader.get("baseUrl"));
    }

    public void login(String user, String pass) {
        WaitUtils.type(username, user);
        WaitUtils.type(password, pass);
        WaitUtils.click(loginButton);
    }
}
