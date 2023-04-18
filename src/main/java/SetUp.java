import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SetUp {

    public static final String PAGE_URL = "https://bedarbiai-test-node.azurewebsites.net/4ZpGP";
    public static WebDriver browser;
    public static WebDriver primaryBrowser;
    public static WebDriver secondaryBrowser;
    public static WebDriver thirdBrowser;

    public static WebDriver launchBrowser() {
        String browserType = System.getenv("BROWSER");

        WebDriver browser;
        if ("firefox".equals(browserType)) {
            FirefoxOptions options = new FirefoxOptions();
            browser = new FirefoxDriver(options);
        } else if ("edge".equals(browserType)) {
            EdgeOptions options = new EdgeOptions();
            options.addArguments("--remote-allow-origins=*");
            browser = new EdgeDriver(options);
        } else {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--remote-allow-origins=*");
            browser = new ChromeDriver(options);
        }
        return browser;
    }

    public static void usePrimaryBrowser() {
        if (primaryBrowser == null) {
            primaryBrowser = launchBrowser();
            primaryBrowser.get(PAGE_URL);
        }
        browser = primaryBrowser;
    }

    public static void useSecondaryBrowser() {
        if (secondaryBrowser == null) {
            secondaryBrowser = launchBrowser();
            secondaryBrowser.get(PAGE_URL);
        }
        browser = secondaryBrowser;
    }

    public static void useThirdBrowser() {
        if (thirdBrowser == null) {
            thirdBrowser = launchBrowser();
            thirdBrowser.get(PAGE_URL);
        }
        browser = thirdBrowser;
    }

    public static void waitForElement(By elementSelector) {
        new WebDriverWait(browser, Duration.ofSeconds(10)).until(e -> e.findElement(elementSelector).isDisplayed());
    }

    public static void waitForNumberOfElements(By elementSelector, int number) {
        new WebDriverWait(browser, Duration.ofSeconds(10)).until(
                ExpectedConditions.numberOfElementsToBe(elementSelector, number)
        );
    }

    public static void jsClick(WebElement element) {
        ((JavascriptExecutor) browser).executeScript("arguments[0].click()", element);
    }

    public static void clickButton(String buttonName) {
        WebElement button = SetUp.browser.findElement(By.xpath("//button[contains(text(),'" + buttonName + "')]"));
        button.click();
    }

    public static String getTextFromPlayerList() {
        SetUp.waitForElement(By.className("connected-users-title"));
        WebElement textWaitingList = SetUp.browser.findElement(By.className("connected-users-title"));
        System.out.println(textWaitingList);
        return textWaitingList.getText();
    }

    public static void close() {
        if (primaryBrowser != null) primaryBrowser.close();
        if (secondaryBrowser != null) secondaryBrowser.close();
        if (thirdBrowser != null) thirdBrowser.close();
        primaryBrowser = null;
        secondaryBrowser = null;
        thirdBrowser = null;
    }
}
