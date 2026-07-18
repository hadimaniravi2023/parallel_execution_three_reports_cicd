package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.DataProvider;
import utils.*;
import utils.ReportGenerator; // Make sure this matches your ReportManager package path

//URl: https://www.google.com/search?q=cucumber+parallel+execution+cross+browser+testing+using+testng+complete+code+with+example&gs_lcrp=EgZjaHJvbWUyBggAEEUYOdIBCTQyNzY1ajBqN6gCFLACAfEFxlAqshPGoITxBcZQKrITxqCE&client=ms-android-transsion&sourceid=chrome-mobile&ie=UTF-8&zx=1776061879451&udm=50&fbs=ADc_l-aHJKCxetkbp8HihrVlWP2E1iNtw1c6Bqm2EL8gFYaWSYxrIPO0Uw9d1XjTJ7RJppKOvWZIYKmAECjL9gYQDgZsZvnowLzm_ylV_jY9dsnO__CeMJDpYQhJ-TAou0SyujRUb_wQUbPVt-ishyxcR5tdUUrbXucZwC0zhm3lM4ovChqG8OBgnkSsivhISN6W_FsMzN2zHV1Hy5KLu9_BPp9UfreIjWAauWkNnaoKwLR8CQIwJlk&aep=10&ntc=1&mstk=AUtExfBugF_mNbl9eyMLxFvmzawNhWoWHMyCPuPvpUWktZJ2YCZVKAGGb9dwmxBs63Zg_uWCkRgcR3ooeAyDlUFoAOpEOLRIqusi8bRlh3E8tl4EnxhbxbIrl2flXclELq6xhawkZ7SDIfR0Vs8u59rBtSlmbXX1lCq3YbwannDDdxyUt33uHmaeXPn00ZMmSLbd2wXFSmD0A63-UbVpCN3v7a9JtS6-UnhrJHstB-KhlsEPwlRm-1CUFZu4zV_mXG0ZhLyXut-DTOcw5gSvUW-q4tpSFxLC7cVK_aftLD7VhAwgLZvmYglR0x17xTQsqNmYauwcN1aXJDKycw&csuir=1&aioh=3&mtid=XY7caZz4CLyYnesPwKaumAo#lfId=ChxjMe
// mvn test -Dbrowser="firefox" or mvn test -Dbrowser="chrome"
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepDefinations", "hooks"},
        plugin = {"pretty",
                //"html:target/cucumber-reports.html",
                //"html:test-output/cucumber-report.html",
                //"json:test-output/cucumber.json",
                // "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                //"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
                "html:target/cucumber-reports/cucumber.html",              // 🌐 Generates Cucumber HTML Report
                "json:target/cucumber-reports/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",         // 🧬 Generates Allure Data
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" // 📊 Generates Extent Report
        }
)
public class TestngRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    //Cucumber Html Report

    /**
     * TestNG AfterSuite runs exactly ONCE after all parallel scenarios have completed execution.
     * This guarantees that cucumber.json is fully written before we attempt to parse it.
     */
    @AfterSuite
    public void generateCustomReport() {
        System.out.println("==========================================================");
        System.out.println("All parallel test executions finished. Compiling Custom HTML Dashboard...");
        System.out.println("==========================================================");
        try {
            // Short delay to give the file system a moment to completely release cucumber.json
            Thread.sleep(1000);

            // Calls the custom reporting tool with graphs/tables and timestamps

            /*ReportGenerator.generateHTMLReport();
            ReportGenerator.generateExtentReport();
            ReportGenerator.generateAllureReport();*/

            // 1. Safely writes all pooled memory data out to Extent Report
            ExtentReportManager.flushReport();

            //Not Required Here to Call Extent Report
            // ReportGenerator.generateExtentReport();

            // 2. Compiles the masterthought cucumber reporting dependencies
            ReportGenerator.generateHTMLReport();

            // 3. Converts JSON system maps into Allure's graphical interface
            //ReportGenerator.generateAllureReport();

        } catch (InterruptedException e) {
            System.err.println("Report generation interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}


