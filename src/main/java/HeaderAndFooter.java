import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class HeaderAndFooter {

    public static final By ACCEPT_COOKIE_BUTTON = By.xpath("//button[contains(.,'Accept')]");
    public static final By LOGO = By.id("link-to-festo");
    public static final By CLIKED_LINK_RESULT = By.tagName("h1");

    public static void acceptAllCookies() {
        SetUp.waitForElement(ACCEPT_COOKIE_BUTTON);
        SetUp.browser.findElement(ACCEPT_COOKIE_BUTTON).click();
    }

    public static void clickFestoLogo() {
        WebElement logo = SetUp.browser.findElement(LOGO);
        logo.click();
        acceptAllCookies();
    }
    public static void clickFooterLink(String text) {
        WebElement link = SetUp.browser.findElement(By.partialLinkText(text));
        SetUp.jsClick(link);
        HeaderAndFooter.acceptAllCookies();
    }

    public static String getFootersClickedLinkResults() {
       SetUp.waitForElement(CLIKED_LINK_RESULT);
        WebElement heading = SetUp.browser.findElement(CLIKED_LINK_RESULT);
        return heading.getText();
    }
}
