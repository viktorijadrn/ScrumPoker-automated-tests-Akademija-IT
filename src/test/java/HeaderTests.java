import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HeaderTests {
    @Before
    public void setUp() {
        SetUp.usePrimaryBrowser();
    }

    @Test
    public void festoLogoTest() {
        HeaderAndFooter.clickFestoLogo();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertTrue("Festo Homepage was not opened", url.contains("https://www.festo.com"));
    }

    @Test
    public void festoLogoNegativeTest() {
        HeaderAndFooter.clickFestoLogo();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertNotEquals("https://www.festo", url);
    }

    @Test
    public void loginFunctionalityTest() {
        LoginPage.clickLogin();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertEquals("Headers login link does not open moderator login page",
                "https://bedarbiai-test-node.azurewebsites.net/adminlogin", url);
    }

    @Test
    public void loginFunctionalityNegativeTest() {
        LoginPage.clickLogin();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertNotEquals("https://bedarbiai-test-node.azurewebsites.net/adminl", url);
    }

    @Test
    public void loginButtonFunctionalityTest() {
        LoginPage.clickButtonLogin();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertEquals("Login button does not open moderators login page","https://bedarbiai-test-node.azurewebsites.net/adminlogin/4ZpGP", url);
    }

    @Test
    public void loginButtonFunctionalityNegativeTest() {
        LoginPage.clickButtonLogin();
        String url = SetUp.browser.getCurrentUrl();
        Assert.assertNotEquals("https://bedarbiai-test-node.azurewebsites.net/admiogin", url);
    }

    @After
    public void teardown() {
        SetUp.close();
    }
}
