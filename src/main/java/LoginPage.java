import org.junit.Assert;
import org.openqa.selenium.*;

public class LoginPage {

    public static final String ERROR_MESSAGE = "You have provided invalid credentials";
    public static final String USER_NAME = "Viktorija";
    public static final String MODERATOR_NAME = "testas";
    public static final String MODERATOR_CORECT_EMAIL = "test@test.com";
    public static final String MODERATOR_CORRECT_PASSWORD = "test";
    public static final String MODERATOR_INCORECT_EMAIL = "test@tess.com";
    public static final String MODERATOR_INCORECT_PASSWORD = "bla";
    public static final String GENERIC_APP_LINK = "https://bedarbiai-test-node.azurewebsites.net/";
    public static final By LOGIN_BUTTON = By.xpath("//button[contains(.,'Login')]");
    public static final By LOGIN_LINK = By.partialLinkText("Login");
    public static final By NAME_INPUT = By.xpath("//input[@placeholder=\"Enter your name\"]");
    public static final By EMAIL_INPUT = By.xpath("//input[@placeholder='Enter your email']");
    public static final By PASSWORD_INPUT = By.xpath("//input[@placeholder=\"Enter your password\"]");
    public static final By ENTER_BUTTON = By.xpath("//button[contains(text(),'Enter')]");
    public static final By USER_NAME_IN_HEADER = By.id("head-username");
    public static final By ERROR = By.className("alert-danger");
    public static final By LOGOUT_LINK = By.partialLinkText("Logout");
    public static final By HOME_TEXT = By.className("text-center");


    public static void clickButtonLogin() {
        WebElement loginButton = SetUp.browser.findElement(LOGIN_BUTTON);
        loginButton.click();
    }

    public static void clickLogin() {
        WebElement login = SetUp.browser.findElement(LOGIN_LINK);
        login.click();
    }

    public static WebElement findLoginInput() {
        SetUp.waitForElement(NAME_INPUT);
        WebElement input = SetUp.browser.findElement(NAME_INPUT);
        return input;
    }

    public static void loginAsPlayer(String username) {
        enterPlayerName(username);
        WebElement enterButton = SetUp.browser.findElement(ENTER_BUTTON);
        enterButton.click();
    }

    public static String getUserLoginResults() {
        SetUp.waitForElement(USER_NAME_IN_HEADER);
        WebElement playerName = SetUp.browser.findElement(USER_NAME_IN_HEADER);
        String name = playerName.getText();
        return name;
    }

    public static void enterPlayerName(String username) {
        WebElement usernameInput = findLoginInput();
        Assert.assertTrue("username input is disabled", usernameInput.isEnabled());
        usernameInput.sendKeys(username);
    }

    public static void enterEmailStep(String email) {
        WebElement input = SetUp.browser.findElement(EMAIL_INPUT);
        Assert.assertEquals("email", input.getAttribute("type"));
        Assert.assertTrue("email input is disabled", input.isEnabled());
        input.sendKeys(email);
    }

    public static void enterPasswordStep(String password) {
        WebElement input = SetUp.browser.findElement(PASSWORD_INPUT);
        Assert.assertEquals("password", input.getAttribute("type"));
        Assert.assertTrue("password input is disabled", input.isEnabled());
        input.sendKeys(password);
    }

    public static void loginAsModerator(String email, String password) {
        enterEmailStep(email);
        enterPasswordStep(password);
        clickButtonLogin();
    }

    public static String readLoginErrorMessage() {
        SetUp.waitForElement(ERROR);
        WebElement errorMessage = SetUp.browser.findElement(ERROR);
        String message = errorMessage.getText();
        System.out.println(message);
        return message;
    }
    public static String findHomePageText() {
        WebElement homeText = SetUp.browser.findElement(HOME_TEXT);
        System.out.println(homeText.getText());
        return homeText.getText();
    }

    public static void logoutUser() {
        SetUp.waitForElement(LOGOUT_LINK);
        WebElement logoutLink = SetUp.browser.findElement(LOGOUT_LINK);
        logoutLink.click();
    }
}
