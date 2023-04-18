import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class LoginTests {
    @Before
    public void setUp() {
        SetUp.usePrimaryBrowser();
    }

    @Test
    public void playerLoginTest() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        Assert.assertEquals("PLayer Viktorija was not loged in",
                "Viktorija", LoginPage.getUserLoginResults());
    }

    @Test
    public void playerLoginTestNegative() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        Assert.assertNotEquals("Viktori", LoginPage.getUserLoginResults());
    }

    @Test
    public void playerLogin25SimbolUsernameTest() {
        LoginPage.loginAsPlayer("z30-=/'p;,xdaweorlfgbmnj!");
        Assert.assertEquals("Player with user name 'z30-=/'p;,xdaweorlfgbmnj!' was not loged in",
                "z30-=/'p;,xdaweorlfgbmnj!", LoginPage.getUserLoginResults());
    }
    @Test
    public void playerLogin26SimbolUsernameTest() {
        LoginPage.loginAsPlayer("z30-=/'p;,xdaweorlfgbmnj!2");
        Assert.assertNotEquals("Player with user name 'z30-=/'p;,xdaweorlfgbmnj!' was not loged in",
                "z30-=/'p;,xdaweorlfgbmnj!2", LoginPage.getUserLoginResults());
    }

    @Test
    public void playerLoginOneSymbolTest() {
        LoginPage.loginAsPlayer("±");
        Assert.assertEquals("Player with simbol ± was not loged in","±", LoginPage.getUserLoginResults());
    }

    @Test
    public void playerEmptyLoginTest() {
        WebElement input = LoginPage.findLoginInput();
        LoginPage.enterPlayerName("");
        Assert.assertEquals("Player name input does not have attribute required","true",
                input.getAttribute("required"));
    }

    @Test
    public void moderatorCorectLoginTest() {
        LoginPage.clickButtonLogin();
        LoginPage.enterEmailStep(LoginPage.MODERATOR_CORECT_EMAIL);
        LoginPage.enterPasswordStep(LoginPage.MODERATOR_CORRECT_PASSWORD);
        LoginPage.clickButtonLogin();
        SetUp.waitForElement(By.partialLinkText("Logout"));
        String moderatorName = LoginPage.getUserLoginResults();
        Assert.assertEquals("Moderator was not loged in",LoginPage.MODERATOR_NAME, moderatorName);
    }

    @Test
    public void moderatorEntersCorectMailIncorectPasswordTest() {
        LoginPage.clickButtonLogin();
        LoginPage.enterEmailStep(LoginPage.MODERATOR_CORECT_EMAIL);
        LoginPage.enterPasswordStep(LoginPage.MODERATOR_INCORECT_PASSWORD);
        LoginPage.clickButtonLogin();
        String errorMessage = LoginPage.readLoginErrorMessage();
        Assert.assertEquals("Error message of incorect credentials was not shown",LoginPage.ERROR_MESSAGE, errorMessage);
    }

    @Test
    public void moderatorEntersIncorectMailCorectPasswordTest() {
        LoginPage.clickButtonLogin();
        LoginPage.enterEmailStep(LoginPage.MODERATOR_INCORECT_EMAIL);
        LoginPage.enterPasswordStep(LoginPage.MODERATOR_CORRECT_PASSWORD);
        LoginPage.clickButtonLogin();
        String errorMessage = LoginPage.readLoginErrorMessage();
        Assert.assertEquals("Error message of incorect credentials was not shown",LoginPage.ERROR_MESSAGE, errorMessage);
    }

    @Test
    public void moderatorEntersIncorectLongMailCorectPasswordTest() {
        LoginPage.clickButtonLogin();
        LoginPage.enterEmailStep("kjhk8jhkhjkjhkjh@adfgdafgadgfgkbmnbmbmnbmnbmhdfgh.com");
        LoginPage.enterPasswordStep(LoginPage.MODERATOR_CORRECT_PASSWORD);
        LoginPage.clickButtonLogin();
        String errorMessage = LoginPage.readLoginErrorMessage();
        Assert.assertEquals("Error message of incorect credentials was not shown",LoginPage.ERROR_MESSAGE, errorMessage);
    }

    @Test
    public void moderatorEntersCorectMailLongPasswordTest() {
        LoginPage.clickButtonLogin();
        LoginPage.enterEmailStep(LoginPage.MODERATOR_CORECT_EMAIL);
        LoginPage.enterPasswordStep("qwerty!@#*()+=_08±!uiopwertyuiopwertyuiopwertyuiopwevynv54tv467ub456");
        LoginPage.clickButtonLogin();
        String errorMessage = LoginPage.readLoginErrorMessage();
        Assert.assertEquals("Error message of incorect credentials was not shown",LoginPage.ERROR_MESSAGE, errorMessage);
    }

    @Test
    public void playerLogoutTest() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        LoginPage.logoutUser();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertEquals("Player logout was unsuccesful", LoginPage.GENERIC_APP_LINK, url);
    }

    @Test
    public void playerLogoutNegativeTest() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        LoginPage.logoutUser();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertNotEquals("SetUp.PAGE_URL", url);
    }

    @Test
    public void moderatorLogoutTest() {
        LoginPage.clickButtonLogin();
        LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL, LoginPage.MODERATOR_CORRECT_PASSWORD);
        LoginPage.logoutUser();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertEquals("Moderator logout was unsuccesful",LoginPage.GENERIC_APP_LINK, url);
    }

    @Test
    public void moderatorLogoutNegativeTest() {
        LoginPage.clickButtonLogin();
        LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL, LoginPage.MODERATOR_CORRECT_PASSWORD);
        LoginPage.logoutUser();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertNotEquals("SsgL", url);
    }
    @Test
    public void moderatorLogsInAndUniqueRoomLinkGeneratesTest(){
       SetUp.primaryBrowser.get(LoginPage.GENERIC_APP_LINK);
       LoginPage.clickLogin();
       LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL,LoginPage.MODERATOR_CORRECT_PASSWORD);
       SetUp.waitForElement(LoginPage.LOGOUT_LINK);
       String firstUrl = SetUp.browser.getCurrentUrl();
       System.out.println(firstUrl);
       LoginPage.logoutUser();
       LoginPage.clickLogin();
       LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL,LoginPage.MODERATOR_CORRECT_PASSWORD);
       SetUp.waitForElement(LoginPage.LOGOUT_LINK);
       String secondtUrl = SetUp.browser.getCurrentUrl();
       System.out.println(secondtUrl);
       Assert.assertNotEquals("Url was not unique",firstUrl,secondtUrl);
    }
    @Test
    public void homePageTextCheckTest() {
        SetUp.primaryBrowser.get(LoginPage.GENERIC_APP_LINK);
        String homeText = LoginPage.findHomePageText();
        Assert.assertEquals("Please use specific room link or login as a Moderator.", homeText);
    }

    @After
    public void teardown() {
       SetUp.close();
    }
}
