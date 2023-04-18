import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class Player {

    public static void playerSelectCard(String text) {
        new WebDriverWait(SetUp.browser, Duration.ofSeconds(10)).until(e -> e.findElement(By.xpath("//button[contains(.,'" + text + "')]")).isEnabled());
        WebElement card = SetUp.browser.findElement(By.xpath("//button[contains(.,'" + text + "')]"));
        ((JavascriptExecutor) SetUp.browser).executeScript("arguments[0].click()", card);
    }

    public static List<String> getVotingAreaCardsOfPlayer() {
        SetUp.waitForElement(By.cssSelector(".voting-area button"));
        List<String> cards = SetUp.browser
                .findElements(By.cssSelector(".voting-area button"))
                .stream().map(e -> e.getDomProperty("value")).collect(Collectors.toList());
        return cards;
    }

    public static String cardSearchingResults() {
        WebElement card = SetUp.browser.findElement(By.className("clicked"));
        String result = card.getText();
        return result;
    }

    public static WebElement findUser(String user) {
        return SetUp.browser.findElement(
                By.xpath("//div[contains(@class, 'player-list')]//h6[text()[contains(., '" + user + "')]]"));
    }

    public static void waitForUserToBeDisplayed(String player) {
        SetUp.waitForElement(
                By.xpath("//div[contains(@class, 'player-list')]//h6[text()[contains(., '" + player + "')]]"));
    }

    public static boolean findVotingSummary() {
        try {
            SetUp.waitForElement(By.cssSelector("div.voting-area > canvas"));
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }

    public static boolean FindCheckMark() {
        try {
            SetUp.waitForElement(By.id("voted-checkmark"));
            return true;
        } catch (Throwable ex) {
            return false;
        }
    }
}
