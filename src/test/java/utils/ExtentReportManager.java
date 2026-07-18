package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> scenarioTest = new ThreadLocal<>();

    public static synchronized ExtentReports getReportInstance() {
        if (extent == null) {
            /*//Working Below Code for single Report and not storing the Previous Record
            String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport.html";
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("BDD Automation Report");
            spark.config().setDocumentTitle("Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Automation Engineer");
            extent.setSystemInfo("Environment", "QA");*/


            // 1. Generate timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            // 2. Define the parent directory for this specific execution run
            String runDirectoryPath = System.getProperty("user.dir") + "/test-output/ExtentReports/Runravi_" + timestamp;

            // 3. Define paths for the HTML report and a separate subfolder for screenshots
            String reportPath = runDirectoryPath + "/ExtentReport.html";
            //Note: Not Required below it will create screenshot Folder Under File with Time stamp
            //String screenshotPath = runDirectoryPath + "/Screenshots";

            // 4. Create the directories safely
            File reportFolder = new File(runDirectoryPath);
            //Note: Not Required below it will create screenshot Folder Under File with Time stamp
            //File screenshotFolder = new File(screenshotPath);

            if (!reportFolder.exists()) {
                reportFolder.mkdirs(); // Creates the Run_timestamp folder
            }
            //Note: Not Required below it will create screenshot Folder Under File with Time stamp
            /* if (!screenshotFolder.exists()) {
                screenshotFolder.mkdirs(); // Creates the nested Screenshots folder
            }*/

            // 5. Initialize the Spark reporter pointing to the file path
            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("BDD Automation Report");
            spark.config().setDocumentTitle("Execution Report");

            // 6. Set up Extent engine configurations
            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("Tester", "Automation Engineer");
            extent.setSystemInfo("Environment", "QA");

            // Optional: Save the snapshot path to a system property so your ScreenshotUtil can access it dynamically

            //Note: Not Required below it will create screenshot Folder Under File with Time stamp
            //System.setProperty("current.run.screenshot.dir", screenshotPath);
        }
        return extent;
    }

    public static void createScenarioTest(String scenarioName) {
        ExtentTest test = getReportInstance().createTest(scenarioName);
        scenarioTest.set(test);
    }

    public static synchronized ExtentTest getScenario() {
        return scenarioTest.get();
    }

    public static void flushReport() {
        if (extent != null) {
            extent.flush();
        }
    }
}
