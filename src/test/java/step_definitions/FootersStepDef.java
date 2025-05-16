package step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import pages.FooterPage;
import utils.BrowserUtils;
import utils.DriverUtils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.Map;

public class FootersStepDef {

    private final FooterPage footerPage = new FooterPage();
    private final String mainWindow = DriverUtils.getDriver().getWindowHandle();

    @When("I click on the following footer items:")
    public void iClickOnTheFollowingFooterItems(DataTable table) {
        List<Map<String,String>> rows = table.asMaps();
        for (Map<String,String> row : rows) {
            String item        = row.get("footerItem");
            String expectedUrl = row.get("expectedUrlPart");

            // find href
            WebElement el = footerPage.footerLinks.stream()
                    .filter(e -> e.getText().trim().equalsIgnoreCase(item))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Footer link not found: " + item));

            String href = el.getAttribute("href");
            if (href == null || href.isBlank()) {
                href = el.findElement(By.xpath("ancestor::a")).getAttribute("href");
            }

            // delegate to BrowserUtils
            BrowserUtils.openUrlInNewTabAndAssert(href, expectedUrl, mainWindow);
        }
    }

    @When("I click on the following social media icons:")
    public void iClickOnTheFollowingSocialMediaIcons(DataTable table) {
        List<Map<String,String>> rows = table.asMaps();
        for (Map<String,String> row : rows) {
            String iconName    = row.get("socialMedia");
            String expectedUrl = row.get("socialMediaUrlPart");

            WebElement icon = switch (iconName.toLowerCase()) {
                case "facebook"  -> footerPage.facebookIcon;
                case "linkedin"  -> footerPage.linkedinIcon;
                case "twitter"   -> footerPage.twitterIcon;
                case "instagram" -> footerPage.instagramIcon;
                default -> throw new RuntimeException("Unknown social icon: " + iconName);
            };

            String href = icon.getAttribute("href");
            BrowserUtils.openUrlInNewTabAndAssert(href, expectedUrl, mainWindow);
        }
    }
}
