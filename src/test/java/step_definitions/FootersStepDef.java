package step_definitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.FooterPage;
import utils.DriverUtils;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class FootersStepDef {

    private final FooterPage footerPage = new FooterPage();
    private final WebDriver driver = DriverUtils.getDriver();
    // capture main window handle once, so it's never null
    private final String mainWindow = driver.getWindowHandle();

    @When("I click on the following footer items:")
    public void iClickOnTheFollowingFooterItems(DataTable table) {
        List<Map<String, String>> rows = table.asMaps();

        for (Map<String, String> row : rows) {
            String footerItem    = row.get("footerItem");
            String expectedUrl   = row.get("expectedUrlPart");

            // 1) find the footer link element
            WebElement link = footerPage.footerLinks.stream()
                    .filter(e -> e.getText().trim().equalsIgnoreCase(footerItem))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Footer link not found: " + footerItem));

            // 2) extract href (or from its ancestor <a>)
            String href = link.getAttribute("href");
            if (href == null || href.isBlank()) {
                href = link.findElement(By.xpath("ancestor::a[1]")).getAttribute("href");
            }

            // 3) remember existing window handles
            Set<String> before = driver.getWindowHandles();

            // 4) open link in new tab
            ((JavascriptExecutor) driver)
                    .executeScript("window.open(arguments[0], '_blank');", href);

            // 5) switch to the newly opened tab
            Set<String> after = driver.getWindowHandles();
            after.removeAll(before);
            String newHandle = after.iterator().next();
            driver.switchTo().window(newHandle);

            // 6) wait for navigation and assert URL
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(expectedUrl));
            Assert.assertTrue(
                    "Expected URL to contain '" + expectedUrl + "', but was: " + driver.getCurrentUrl(),
                    driver.getCurrentUrl().toLowerCase().contains(expectedUrl.toLowerCase())
            );

            // 7) close tab and return to main window
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    @When("I click on the following social media icons:")
    public void iClickOnTheFollowingSocialMediaIcons(DataTable table) {
        List<Map<String, String>> rows = table.asMaps();

        for (Map<String, String> row : rows) {
            String socialMedia  = row.get("socialMedia");
            String expectedUrl  = row.get("socialMediaUrlPart");

            // 1) select the correct icon element
            WebElement icon;
            switch (socialMedia.toLowerCase()) {
                case "facebook":  icon = footerPage.facebookIcon;  break;
                case "linkedin":  icon = footerPage.linkedinIcon;  break;
                case "twitter":   icon = footerPage.twitterIcon;   break;
                case "instagram": icon = footerPage.instagramIcon; break;
                default: throw new RuntimeException("Unknown social icon: " + socialMedia);
            }

            // 2) open icon href in new tab and switch
            String href = icon.getAttribute("href");
            Set<String> before = driver.getWindowHandles();
            ((JavascriptExecutor) driver)
                    .executeScript("window.open(arguments[0], '_blank');", href);
            Set<String> after = driver.getWindowHandles();
            after.removeAll(before);
            String newHandle = after.iterator().next();
            driver.switchTo().window(newHandle);

            // 3) wait for navigation and assert URL
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(expectedUrl));
            Assert.assertTrue(
                    "Expected URL to contain '" + expectedUrl + "', but was: " + driver.getCurrentUrl(),
                    driver.getCurrentUrl().toLowerCase().contains(expectedUrl.toLowerCase())
            );

            // 4) close tab and return to main window
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }
}
