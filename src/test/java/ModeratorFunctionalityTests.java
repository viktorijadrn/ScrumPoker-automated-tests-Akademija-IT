import org.junit.*;
import org.openqa.selenium.By;
import java.util.Arrays;
import java.util.List;

public class ModeratorFunctionalityTests {

    public static final String PHASE1_MESSAGE = "Waiting for 1 players to vote";
    List<String> cards = Arrays.asList("0", "1/2", "1", "2", "3", "5", "8", "13", "20", "40", "100", "?", "Coffee");
    List<String> specificCards = Arrays.asList("5", "100", "Coffee");
    List<String> savedCardFromTheLatestLogIn = Arrays.asList("8", "13");

    @Before
    public void setUp() {
        SetUp.usePrimaryBrowser();
       LoginPage.clickButtonLogin();
       LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL, LoginPage.MODERATOR_CORRECT_PASSWORD);
    }

    @Test
    public void logedInModeratorIsVisibleInListSectionTest() {
        Player.waitForUserToBeDisplayed("testas");
        Assert.assertEquals("Modertor is not visible after login","Moderator",
                Player.findUser("testas").getAttribute("title"));
    }

    @Test
    public void selectAllCardsTest() {
        Moderator.clickConfigurationButton();
        Moderator.findCardConfigurationModal();
        Moderator.selectAllCards();
        SetUp.clickButton(" Save");
        Moderator.waitForNumberOfCards(cards.size());
        List<String> actualResults = Moderator.getVotingAreaCardsOfModerator();
        Assert.assertEquals("Moderator can't select all cards",cards, actualResults);
    }

    @Test
    public void selectSpecificCardsTest() {
        Moderator.clickConfigurationButton();
        Moderator.findCardConfigurationModal();
        Moderator.resetCardsConfiguration();
        Moderator.selectCardCheckbox("5");
        Moderator.selectCardCheckbox("100");
        Moderator.selectCardCheckbox("Coffee");
        SetUp.clickButton("Save");
        Moderator.waitForNumberOfCards(specificCards.size());
        List<String> actualResults = Moderator.getVotingAreaCardsOfModerator();
        Assert.assertEquals("Moderator can't select specific cards 5 100 coffee",specificCards, actualResults);
    }

    @Test
    public void votingConfigCancelTest() {
        List<String> expectedResults = Moderator.getVotingAreaCardsOfModerator();
        Moderator.clickConfigurationButton();
        Moderator.findCardConfigurationModal();
        SetUp.clickButton("Cancel");
        List<String> actualResults = Moderator.getVotingAreaCardsOfModerator();
        Assert.assertEquals("Button cancel does not work",expectedResults, actualResults);
    }

    @Test
    public void moderatorClearsVotesTest() {
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("vika");
        Player.waitForUserToBeDisplayed("vika");
        Player.playerSelectCard("8");
        SetUp.usePrimaryBrowser();
        Moderator.waitForNumberOfVotedPlayersToBe(1);
        String beforeResults = Player.findUser("vika").getText();
        System.out.println(beforeResults);
        SetUp.clickButton("Clear Votes");
        Moderator.waitForNumberOfVotedPlayersToBe(0);
        String afterResults = Player.findUser("vika").getText();
        System.out.println(afterResults);
        Assert.assertNotEquals("Button clear votes does not clears votes",beforeResults,afterResults);
    }

    @Test
    public void moderatorFlipsCardsTest(){
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("player1");
        Player.waitForUserToBeDisplayed("player1");
        SetUp.useThirdBrowser();
        LoginPage.loginAsPlayer("player2");
        Player.waitForUserToBeDisplayed("player2");
        Player.playerSelectCard("8");
        SetUp.waitForNumberOfElements(By.id("voted-checkmark"),1);
        SetUp.usePrimaryBrowser();
        SetUp.clickButton("Flip Cards");
        Moderator.waitForNumberOfVotedPlayersToBe(2);
        Assert.assertEquals("player1\n?", Player.findUser("player1").getText());
        Assert.assertEquals("player2\n8", Player.findUser("player2").getText());
    }

    @Test
    public void moderatorClicksFinishVotingTest(){
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("Player");
        Player.waitForUserToBeDisplayed("Player");
        Player.playerSelectCard("8");
        SetUp.usePrimaryBrowser();
        Moderator.waitForNumberOfVotedPlayersToBe(1);
        String beforeResults = Player.findUser("Player").getText();
        SetUp.clickButton("Finish Voting");
        Moderator.waitForNumberOfVotedPlayersToBe(0);
        String afterResults = Player.findUser("Player").getText();
        Assert.assertNotEquals("Button finish voting does not restart voting proces",beforeResults,afterResults);
    }

    @Test
    public void phaseWaitingForPLayersToVoteIsVisibleAfterClearVoteTest(){
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("zmogus");
        Player.waitForUserToBeDisplayed("zmogus");
        Player.playerSelectCard("8");
        SetUp.usePrimaryBrowser();
        SetUp.clickButton("Clear Votes");
        String actual = SetUp.getTextFromPlayerList();
        Assert.assertEquals("Phase 'Waiting for 1 players to vote' is not visible after moderator clear votes ",
                PHASE1_MESSAGE,actual);
    }

    @Test
    public void whoDidntVoteTest() {
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("player1");
        Player.waitForUserToBeDisplayed("player1");
        SetUp.usePrimaryBrowser();
        SetUp.clickButton("Flip Cards");
        String result = Moderator.findVote();
        Assert.assertEquals("? was not fshown with player who did't wote","?",result);
    }
 
    @Test
    public void phaseWaitingForPLayersToVoteIsVisibleAfterFinishVotingTest() {
        Moderator.waitForCards();
        SetUp.clickButton("Finish Voting");
        String actual = SetUp.getTextFromPlayerList();
        Assert.assertEquals("Phase 'Waiting for 0 players to vote' is not visible after moderator finishes voting",
                "Waiting for 0 players to vote", actual);
    }
    @Test
    public void newValuesSavedForNextTimeTest() {
        Moderator.clickConfigurationButton();
        Moderator.findCardConfigurationModal();
        Moderator.resetCardsConfiguration();
        Moderator.selectCardCheckbox("8");
        Moderator.selectCardCheckbox("13");
        SetUp.clickButton("Save");
        LoginPage.logoutUser();
        SetUp.close();
        SetUp.usePrimaryBrowser();
        LoginPage.clickButtonLogin();
        LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL, LoginPage.MODERATOR_CORRECT_PASSWORD);
        List<String> actualResults = Moderator.getVotingAreaCardsOfModerator();
        Assert.assertEquals("new card values wasn't saved for next time",savedCardFromTheLatestLogIn,actualResults);
    }
    @After
    public void teardown() {
        SetUp.close();
    }
}
