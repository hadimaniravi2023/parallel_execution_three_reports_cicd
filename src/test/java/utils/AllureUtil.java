package utils;

import java.io.ByteArrayInputStream;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AllureUtil {
    private AllureUtil() {
    }

    /**
     * Attach screenshot to Allure report
     */
    public static void attachScreenshot(WebDriver driver,
                                        String screenshotName) {
        try {
            byte[] screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(OutputType.BYTES);

            Allure.addAttachment(
                    screenshotName,
                    new ByteArrayInputStream(screenshot));

        } catch (Exception e) {
            System.out.println(
                    "Unable to attach screenshot to Allure report: "
                            + e.getMessage());
        }
    }

   /* Step 1 :
    mvn test -Dbrowser="chrome"*/

    /*Step 2: How to Run Allure Report?
    Command Prompt:
    allure generate allure-results --clean -o allure-report
    Console:
    Jun 24, 2026 6:14:15 PM io.qameta.allure.CommandLine main
    INFO: APP_HOME is not set, using default configuration
    Report successfully generated to allure-report*/

    /*OR Step 3: To Generate Allure Report
            Command Prompt:
    allure serve allure-results
    Console :
    Jun 24, 2026 6:17:17 PM io.qameta.allure.CommandLine main
    INFO: APP_HOME is not set, using default configuration
    Generating report to temp directory...
    Report successfully generated to C:\Users\hadim\AppData\Local\Temp\4423915693857371195\allure-report
    Starting web server...
    Server started at <http://127.0.0.1:64834>. Press <Ctrl+C> to exit*/

}
