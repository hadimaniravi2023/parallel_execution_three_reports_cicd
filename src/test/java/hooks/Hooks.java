package hooks;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import drivers.DriverManager;
import io.cucumber.java.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import utils.AllureUtil;
import utils.ExtentReportManager;
import utils.ReportGenerator;
import utils.ScreenshotUtil;
import java.io.File;
import java.util.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Hooks {
    /* private static ExtentReports extent= ExtentReportManager.getReportInstance();
    private static ThreadLocal<ExtentTest> test=new ThreadLocal<>();

    // This static variable will hold the active scenario for the running test
    public static Scenario currentScenario; //To Call ScreenshotMethod and Attatch into Report*///Ravi

    // 🧵 ThreadLocal isolates the Scenario object for each parallel thread
    private static ThreadLocal<Scenario> scenarioThreadLocal = new ThreadLocal<>();

    public static Scenario getCurrentScenario() {
        return scenarioThreadLocal.get();
    }

    // Initialize Log4j2 Logger
    private static final Logger log = LogManager.getLogger(Hooks.class);


    //Note : Cucumber Report.txt in local
    @Before
    public void setup(Scenario scenario) {
        /*currentScenario = scenario; //To Call ScreenshotMethod and Attatch into Report*/ //Ravi
        System.out.println("Before Hooks execution 1");
        log.info("Starting Scenario log starts: " + scenario.getName());

        // Retrieve browser type from System properties or TestNG context
        String browser = System.getProperty("browser", "chrome");
        DriverManager.setDriver(browser);

       /* ExtentTest extentTest=extent.createTest(scenario.getName());
        test.set(extentTest);*///Ravi

        // Store the scenario safely inside the current thread's memory
        scenarioThreadLocal.set(scenario);

        // Initialize Extent Report for this parallel execution thread
        ExtentReportManager.createScenarioTest(scenario.getName());
    }


   /* //Note: Below AfterStep is required to add screenshot of each Step defination
   //Extent Report or Allure report
    @AfterStep
    public void afterStep(Scenario scenario){
        String screenshotPath= ScreenshotUtil.captureScreenshot(scenario.getName());
        try{
            test.get().addScreenCaptureFromPath(screenshotPath);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //allure Report
        //AllureUtil.attachScreenshot(ScreenshotUtil.captureScreenshotForAllure());

        //Cucumber-html report-------------------------------------------
        byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
        scenario.attach(screenshot, "image/png", "Step Screenshot");
    }*/

    @After
    public void tearDown(Scenario scenario) {
        //DriverManager.quitDriver();
        /*currentScenario = scenario; //To Call ScreenshotMethod and Attatch into Report*///Ravi

        //Extent Report
        /*String screenshotPath=ScreenshotUtil.captureScreenshot(scenario.getName());
        try{
            if(scenario.isFailed()){
                test.get().fail("Scenario Failed Extent").addScreenCaptureFromPath(screenshotPath);
            }else{
                test.get().pass("Scenario Passed Extent").addScreenCaptureFromPath(screenshotPath);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }//Ravi*/

        if (DriverManager.getDriver() != null) {
            String screenshotPath = ScreenshotUtil.captureScreenshot(scenario.getName());
            //String screenshotPath = capturePhysicalScreenshot(scenario.getName() + "_Final");

            if (ExtentReportManager.getScenario() != null) {
                /*//Working it will attatch Screenshot to Pass and Fail
                if (scenario.isFailed()) {
                    ExtentReportManager.getScenario().fail("Scenario Failed Extent")
                            .addScreenCaptureFromPath(screenshotPath);
                } else {
                    ExtentReportManager.getScenario().pass("Scenario Passed Extent")
                            .addScreenCaptureFromPath(screenshotPath);
                }*/

                //below is also woking but screenshot not attched to last pass result
                if (scenario.isFailed()) {
                    ExtentReportManager.getScenario().fail("Scenario Failed Extent_Ravi")
                            .addScreenCaptureFromPath(screenshotPath);
                } else {
                    ExtentReportManager.getScenario().pass("Scenario Passed Extent_Ravi")
                            .addScreenCaptureFromPath(screenshotPath);
                }
            }
        }

        //Cucumber Html.report-------------------------------------
        if (scenario.isFailed()) {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Failure Screenshot_Cucumber_Tanvi");
        } else {
            byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "Pass Screenshot_cucumber_Tanvi");
        }


        //Allure Report--------------
        //Note : Command to Run from Git Bash : allure serve allure-results OR allure generate allure-results --clean -o allure-report
        //Note: TO Open Report from Git bash: allure open allure-report
       /* //Not Required below it will cucumber html cover
       if (scenario.isFailed()) {
            AllureUtil.attachScreenshot(
                    DriverManager.getDriver(),
                    "Failure Allure Screenshot_RitikaAdded");
        }else{
            AllureUtil.attachScreenshot(
                    DriverManager.getDriver(),
                    "Pass allure Screenshot_RitikaAdded");
        }*/

        DriverManager.quitDriver();
        /*extent.flush();*///Ravi

        // Clean up the thread local variable to prevent memory leaks
        scenarioThreadLocal.remove();
        ExtentReportManager.flushReport();
    }

}
