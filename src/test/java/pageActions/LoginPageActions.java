package pageActions;
import drivers.DriverManager;
import io.cucumber.java.Scenario;
import pageLocators.LoginPageLocators;

public class LoginPageActions {
    //Call Other Pacakges and Classes
    LoginPageLocators loginpagelocators;

    // Constructor initializes the driver and the locators object
    public LoginPageActions(){
        this.loginpagelocators=new LoginPageLocators();
    }

    public void enterUsername(String username){
        DriverManager.getDriver().findElement(loginpagelocators.txtUsername).sendKeys(username);
    }

    public void enterPassword(String password){
        DriverManager.getDriver().findElement(loginpagelocators.txtPassword).sendKeys(password);
    }

    public void clickLogin(){
        DriverManager.getDriver().findElement(loginpagelocators.btnLogin).click();
    }

    public String getDashboardTitle(){
        return DriverManager.getDriver().findElement(loginpagelocators.lblDashboardTitle).getText();
    }
}
