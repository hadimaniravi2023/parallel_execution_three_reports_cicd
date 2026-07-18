package utils;

import com.aventstack.extentreports.MediaEntityBuilder;
import drivers.DriverManager;
import hooks.Hooks;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class ScreenshotUtil {
    public static String captureScreenshot(String scenarioName){
        String timestamp=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String screenshotName=scenarioName.replaceAll(" ","_")+"_"+timestamp+".png";
        String screenshotPath=System.getProperty("user.dir")+"/test-output/screenshots_ravi/"+screenshotName;

        try{
            File srcFile =((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.FILE);
            File desFile=new File(screenshotPath);
            FileUtils.copyFile(srcFile,desFile);
        }catch (Exception e){
            System.out.println("Screenshot capture failed: "+e.getMessage());
        }
        return  screenshotPath;
    }

    public static byte[] captureScreenshotForAllure(){
        return ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

    //Explicity attatch screenshot
    public static void attachScreenshot(Scenario scenario, String screenshotName) {
        byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", screenshotName);
    }

    /* //Note : Not Required below because no need to pass WebDriver driver
    public static void captureAndAttach(WebDriver driver, Scenario scenario, String screenshotName) {
        if (driver != null && scenario != null) {
            try {
                // 1. Capture the screenshot as a byte array
                final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

                // 2. Attach it to the Cucumber report
                scenario.attach(screenshot, "image/png", screenshotName);
                System.out.println("📸 Screenshot successfully attached to report: " + screenshotName);
            } catch (Exception e) {
                System.err.println("❌ Failed to capture external screenshot: " + e.getMessage());
            }
        }
    }*/

    /*//Note: To generate only Allure Reports
    public static void captureAndAttach(Scenario scenario, String screenshotName) {
        if (DriverManager.getDriver() != null && scenario != null) {
            try {
                // 1. Capture the screenshot as a byte array
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

                // 2. Attach it to the Cucumber report
                scenario.attach(screenshot, "image/png", screenshotName);
                System.out.println("📸 Screenshot successfully attached to report: " + screenshotName);
            } catch (Exception e) {
                System.err.println("❌ Failed to capture external screenshot: " + e.getMessage());
            }
        }
    }*/

    public static void captureAndAttach(Scenario scenario, String screenshotName) {
        try {
            if (DriverManager.getDriver() != null) {
                // 1. Capture screenshot as raw bytes
                final byte[] screenshotBytes = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);

                // 2. Attach to Native Cucumber HTML Report AND Allure Report
                if (scenario != null) {
                    scenario.attach(screenshotBytes, "image/png", screenshotName);
                    System.out.println("Embedded in Cucumber HTML & Allure: " + screenshotName);
                } else {
                    System.err.println("Scenario object is null. Skipping Cucumber HTML/Allure attachment.");
                }

                // 3. Convert same bytes to Base64 and attach to Extent Report
                if (ExtentReportManager.getScenario() != null) {
                    /*//Working With Base64 image and we need to Open
                    String base64String = Base64.getEncoder().encodeToString(screenshotBytes);
                    ExtentReportManager.getScenario().info(screenshotName,
                            MediaEntityBuilder.createScreenCaptureFromBase64String("data:image/png;base64," + base64String).build());
                    System.out.println("Embedded in Extent Report: " + screenshotName);*/

                    String screenshotPath=ScreenshotUtil.captureScreenshot(scenario.getName());
                    if (scenario.isFailed()) {
                        ExtentReportManager.getScenario().info(screenshotName,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    } else {
                        ExtentReportManager.getScenario().info(screenshotName,MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    }
                }

            } else {
                System.err.println("WebDriver is null. Cannot capture screenshot.");
            }
        } catch (Exception e) {
            System.err.println("Error during captureAndAttach: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
