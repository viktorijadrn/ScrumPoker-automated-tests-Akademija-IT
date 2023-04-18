import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FooterTests {

    @Before
    public void setUp() {
        SetUp.usePrimaryBrowser();
    }

    @Test
    public void footerImprintLinkTest() {
        HeaderAndFooter.clickFooterLink("Imprint");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertEquals("Festo Imprint page was not opened","Imprint", actualResults);
    }

    @Test
    public void footerImprintLinkTestNegative() {
        HeaderAndFooter.clickFooterLink("Imprint");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertNotEquals("Imprnt", actualResults);
    }

    @Test
    public void footerPrivacyLinkTest() {
        HeaderAndFooter.clickFooterLink("Data privacy");
        String actualResults =  HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertEquals("Festo Data privay page was not opened","Festo Data Privacy Statement", actualResults);
    }

    @Test
    public void footerPrivacyLinkTestNegative() {
        HeaderAndFooter.clickFooterLink("Data privacy");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertNotEquals("Festo Data Privacy", actualResults);
    }

    @Test
    public void footerTermsLinkTest() {
        HeaderAndFooter.clickFooterLink("Terms and Conditions of Sale");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertEquals("Festo Terms page was not opened","Terms and Conditions of Sale",actualResults);
    }

    @Test
    public void footerTermsLinkTestNegative() {
        HeaderAndFooter.clickFooterLink("Terms and Conditions of Sale");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertNotEquals("SetUp.TEXT_RESULTS", actualResults);
    }

    @Test
    public void footerServisLinkTest() {
        HeaderAndFooter.clickFooterLink("Cloud service");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertEquals("Festo Servis page was not opened","Cloud Services", actualResults);
    }

    @Test
    public void footerServisLinkTestNegative() {
        HeaderAndFooter.clickFooterLink("Cloud service");
        String actualResults = HeaderAndFooter.getFootersClickedLinkResults();
        Assert.assertNotEquals("Cloud", actualResults);
    }

    @After
    public void teardown() {
        SetUp.close();
    }
}