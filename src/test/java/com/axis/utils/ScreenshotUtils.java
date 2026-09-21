package com.axis.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class ScreenshotUtils {
    private ScreenshotUtils() {}

    public static byte[] captureBytes() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    public static String saveScreenshot(String name) {
        try {
            Path dir = Paths.get(ConfigReader.get("screenshot.dir"));
            Files.createDirectories(dir);
            byte[] bytes = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);
            Path file = dir.resolve(name + "_" + System.currentTimeMillis() + ".png");
            Files.write(file, bytes);
            return file.toString();
        } catch (Exception e) {
            return "Screenshot failed: " + e.getMessage();
        }
    }
}
