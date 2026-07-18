package drivers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class DriverManager {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void setDriver(String browser) {
        System.out.println("set Driver Execution 2");
        if (browser.equalsIgnoreCase("chrome")) {
            //Note : Working for Chrome
            driver.set(new ChromeDriver());
        } else if (browser.equalsIgnoreCase("firefox")) {
            //Note : Working for Firefox
            driver.set(new FirefoxDriver());
        }
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}
