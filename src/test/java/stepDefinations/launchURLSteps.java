package stepDefinations;
import drivers.DriverManager;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import utils.ScreenshotUtil;

public class launchURLSteps {
    private static final Logger log = LogManager.getLogger(launchURLSteps.class);
    @Given("I launch the URL {string}")
    public void i_launch_the_url(String url) {
        System.out.println("Given I launch the URL execution 3");
        log.info("launcURLSteps");
        DriverManager.getDriver().get(url);
        System.out.println("Running on Thread: " + Thread.currentThread().getId());
    }

    @And("Enter {string} and click on search")
    public void enterAndClickOnSearch(String name) throws InterruptedException {
        DriverManager.getDriver().findElement(By.cssSelector("textarea[name=\"q\"]")).sendKeys(name);
        //ScreenshotUtil.captureScreenshot("Enter the :"+name +" in Searchbox");
        Thread.sleep(2000);
        ScreenshotUtil.captureScreenshot("Enter the text in search box");
        Thread.sleep(2000);
        DriverManager.getDriver().findElement(By.xpath("//div[@id='sa_sug_block']/child::ul")).click();
    }
}
