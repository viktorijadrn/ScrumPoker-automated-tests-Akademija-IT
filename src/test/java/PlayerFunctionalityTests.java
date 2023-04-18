import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import java.util.List;

public class PlayerFunctionalityTests {

    public static final String PHASE1_MESSAGE = "Waiting for 1 players to vote";
    public static final String PHASE2_MESSAGE = "Waiting for moderator to finalise vote";

    @Before
    public void setUp() {
        SetUp.usePrimaryBrowser();
    }

    @Test
    public void playersWhoLoggedInAreVisibleInPlayersListSectionTest() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        Player.waitForUserToBeDisplayed(LoginPage.USER_NAME);
        Assert.assertEquals("PLayer Viktorija is not visible after login","Player",
                Player.findUser(LoginPage.USER_NAME).getAttribute("title"));
    }

    @Test
    public void playerCardSelectingTest () {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        Player.playerSelectCard("100");
        Assert.assertEquals("Player can't select card","100", Player.cardSearchingResults());
    }
    
    @Test
    public void playerShoudSeeModeratorsSelectedCardsTest(){
        LoginPage.clickButtonLogin();
        LoginPage.loginAsModerator(LoginPage.MODERATOR_CORECT_EMAIL,LoginPage.MODERATOR_CORRECT_PASSWORD);
        Moderator.clickConfigurationButton();
        Moderator.findCardConfigurationModal();
        Moderator.resetCardsConfiguration();
        Moderator.selectCardCheckbox("Coffee");
        Moderator.selectCardCheckbox("100");
        Moderator.selectCardCheckbox("0");
        SetUp.clickButton("Save");
        List <String> expectedResults = Moderator.getVotingAreaCardsOfModerator();
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("Player1");
        List<String> actualResults = Player.getVotingAreaCardsOfPlayer();
        Assert.assertEquals("Player can't see Moderators selected cards 5 100 coffee",expectedResults, actualResults);
    }

    @Test
    public void phaseWaitingForPLayersToVoteIsVisibleTest(){
        LoginPage.loginAsPlayer("zmogus");
        Player.waitForUserToBeDisplayed("zmogus");
        String actual = SetUp.getTextFromPlayerList();
        Assert.assertEquals("Message 'Waiting for 1 players to vote' is not visible",PHASE1_MESSAGE, actual);
    }
    @Test
    public void phaseWaitingForModeratorToFinalizeVotesIsVisibleTest(){
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("Lukas");
        Player.playerSelectCard("100");
        SetUp.waitForElement(By.tagName("canvas"));
        String actual = SetUp.getTextFromPlayerList();
        LoginPage.logoutUser();
        Assert.assertEquals(PHASE2_MESSAGE, actual);
    }

    @Test
    public void playerVotingSummaryTest () {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        Player.playerSelectCard("100");
        Player.findVotingSummary();
        Assert.assertTrue("Summary not visible", Player.findVotingSummary());
    }

    @Test
    public void playersWhoLoggedInVotedCheckedMarkTest() {
        LoginPage.loginAsPlayer(LoginPage.USER_NAME);
        SetUp.useSecondaryBrowser();
        LoginPage.loginAsPlayer("Nojusss");
        Player.playerSelectCard("100");
        Player.FindCheckMark();
        Assert.assertTrue("Checkmark not visible" , Player.FindCheckMark());
    }

    @After
    public void teardown() {
       SetUp.close();
    }
}
