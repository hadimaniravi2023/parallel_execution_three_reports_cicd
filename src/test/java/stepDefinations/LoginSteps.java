package stepDefinations;
import hooks.Hooks;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import org.testng.Assert;
import pageActions.LoginPageActions;
import utils.ScreenshotUtil;

public class LoginSteps {
    LoginPageActions loginActions=new LoginPageActions(); // Reference to Actions class


    @And("User enters the valid username {string} and Password {string}")
    public void userEntersTheValidUsernameAndPassword(String username, String password) {
        System.out.println("And User enters the valid username");
        loginActions.enterUsername(username);
        /* ScreenshotUtil.captureAndAttach(Hooks.currentScenario,"Username entered Successfully"); //To Call ScreenshotMethod and Attatch into Report*/
        ScreenshotUtil.captureAndAttach(Hooks.getCurrentScenario(),"Username entered Successfully");
        loginActions.enterPassword(password);
        /*ScreenshotUtil.captureAndAttach(Hooks.currentScenario,"Password entered Successfully"); //To Call ScreenshotMethod and Attatch into Report*/
        ScreenshotUtil.captureAndAttach(Hooks.getCurrentScenario(),"Password entered Successfully");
    }

    @And("Click on the Login button")
    public void clickOnTheLoginButton() {
        loginActions.clickLogin();
    }

    @Then("the user should be redirected to the products dashboard")
    public void theUserShouldBeRedirectedToTheProductsDashboard() {
        String actualTitle=loginActions.getDashboardTitle();
        Assert.assertEquals(actualTitle, "Products");
    }
}
