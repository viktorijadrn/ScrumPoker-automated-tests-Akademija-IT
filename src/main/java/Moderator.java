import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Moderator {

    public static WebElement findCardConfigurationModal() {
        WebElement modal = SetUp.browser.findElement(By.className("modal-content"));
        new WebDriverWait(SetUp.browser, Duration.ofSeconds(3)).until(ExpectedConditions.visibilityOf(modal));
        return modal;
    }

    public static void resetCardsConfiguration() {
        selectAllCards();
        selectAllCards();
    }

    public static void selectAllCards() {
        WebElement checkBox = SetUp.browser.findElement(By.className("form-check-input"));
        checkBox.click();
    }

    public static void waitForCards() {
        SetUp.waitForElement(By.cssSelector(".admin-voting-area .disabled-card"));
    }

    public static void waitForNumberOfCards(int number) {
        SetUp.waitForNumberOfElements(By.cssSelector(".admin-voting-area .disabled-card"), number);
    }

    public static List<String> getVotingAreaCardsOfModerator() {
        waitForCards();
        List<String> cards = SetUp.browser
                .findElements(By.cssSelector(".admin-voting-area .disabled-card h5"))
                .stream().map(e -> e.getText()).collect(Collectors.toList());
        return cards;
    }

    public static void clickConfigurationButton() {
        new WebDriverWait(SetUp.browser, Duration.ofSeconds(3)).until(
                e -> SetUp.browser.findElement(By.xpath("//button[contains(text(),'Voting configuration')]")).isDisplayed());
        WebElement configurationButton = SetUp.browser.findElement(By.xpath("//button[contains(text(),'Voting configuration')]"));
        configurationButton.click();
    }

    public static void selectCardCheckbox(String text) {
        SetUp.waitForElement(By.xpath("//label[contains(.,'" + text + "')]"));
        WebElement option = SetUp.browser.findElement(By.xpath("//label[contains(.,'" + text + "')]"));
        option.click();
    }

    public static void waitForNumberOfVotedPlayersToBe(int number) {
        new WebDriverWait(SetUp.browser, Duration.ofSeconds(6)).until(
                ExpectedConditions.numberOfElementsToBe(By.xpath("//span[@title='Vote']"), number));
    }

    public static String findVote() {
        SetUp.waitForElement(By.xpath("//span[@title='Vote']"));
        List<String> votes = SetUp.browser.findElements(By.xpath("//span[@title='Vote']"))
                .stream().map(e -> e.getText()).collect(Collectors.toList());
        System.out.println(votes);
        return votes.get(0);
    }
}
