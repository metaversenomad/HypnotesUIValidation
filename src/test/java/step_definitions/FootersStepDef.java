package step_definitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;
import org.junit.Assert;
import pages.FooterPage;
import utils.DriverUtils;
import utils.BrowserUtils;

import java.time.Duration;
import java.util.*;

public class FootersStepDef {
    private final FooterPage footerPage = new FooterPage();
    private final WebDriver driver = DriverUtils.getDriver();
    private String mainWindow;

    @Given("I open the {string} homepage")
    public void iOpenTheHomepage(String homepageUrl) {
        System.out.println("[DEBUG] Opening homepage: " + homepageUrl);
        driver.get(homepageUrl);
        mainWindow = driver.getWindowHandle();
    }

    @When("I click on the {string} footer item")
    public void iClickOnTheFooterItem(String footerItem) {
        // 1) Elemanı bul
        WebElement footerDiv = null;
        for (WebElement item : footerPage.footerLinks) {
            String text = item.getText().trim();
            System.out.println("[DEBUG] Scanning footer link: \"" + text + "\"");
            if (text.equalsIgnoreCase(footerItem)) {
                footerDiv = item;
                System.out.println("[DEBUG] Matched footer link: " + text);
                break;
            }
        }
        Assert.assertNotNull("Footer link bulunamadı: " + footerItem, footerDiv);

        // 2) href tespit et (div içi veya parent <a>)
        WebElement anchor = footerDiv;
        String href = footerDiv.getAttribute("href");
        if (href == null || href.isBlank()) {
            anchor = footerDiv.findElement(By.xpath("ancestor::a[1]"));
            href = anchor.getAttribute("href");
        }
        Assert.assertNotNull("href bulunamadı: " + footerItem, href);
        System.out.println("[DEBUG] href = " + href);

        // 3) window handle’ları capture et
        Set<String> before = driver.getWindowHandles();
        System.out.println("[DEBUG] before handles: " + before);

        // 4) Yeni sekmede aç
        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", href);

        BrowserUtils.waitFor(1);

        // 5) Yeni handle’ı bul
        Set<String> after = driver.getWindowHandles();
        System.out.println("[DEBUG] after handles: " + after);
        after.removeAll(before);
        Assert.assertFalse("Yeni pencere açılamadı", after.isEmpty());
        String newHandle = after.iterator().next();
        driver.switchTo().window(newHandle);
        System.out.println("[DEBUG] Switched to footer window: " + driver.getCurrentUrl());

        // 6) URL yüklenmesini bekle
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(href));
        System.out.println("[DEBUG] Footer final URL: " + driver.getCurrentUrl());
    }

    @When("I click on the {string} social media icon")
    public void iClickOnTheSocialMediaIcon(String socialMedia) {
        // 1) İkonu bul
        WebElement icon;
        switch (socialMedia.toLowerCase()) {
            case "facebook":  icon = footerPage.facebookIcon;  break;
            case "linkedin":  icon = footerPage.linkedinIcon;  break;
            case "twitter":   icon = footerPage.twitterIcon;   break;
            case "instagram": icon = footerPage.instagramIcon; break;
            default: throw new IllegalArgumentException("Bilinmeyen sosyal ikon: " + socialMedia);
        }

        // 2) href ve handle’ları capture et
        String href = icon.getAttribute("href");
        Assert.assertNotNull("Sosyal ikon href boş: " + socialMedia, href);
        System.out.println("[DEBUG] Sosyal icon href = " + href);
        Set<String> before = driver.getWindowHandles();
        System.out.println("[DEBUG] before handles: " + before);

        // 3) Yeni sekmede aç
        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", href);
        BrowserUtils.waitFor(1);

        // 4) Yeni handle’ı bul ve switch et
        Set<String> after = driver.getWindowHandles();
        System.out.println("[DEBUG] after handles: " + after);
        after.removeAll(before);
        Assert.assertFalse("Yeni sosyal pencere açılamadı", after.isEmpty());
        String newHandle = after.iterator().next();
        driver.switchTo().window(newHandle);
        System.out.println("[DEBUG] Switched to social window: " + driver.getCurrentUrl());
    }

    @Then("I should be redirected to a page containing {string}")
    public void iShouldBeRedirectedToAPageContaining(String expected) {
        BrowserUtils.waitFor(2);
        String current = driver.getCurrentUrl().toLowerCase();
        System.out.println("[DEBUG] Asserting URL contains '" + expected + "': " + current);
        Assert.assertTrue(
                "Beklenen '" + expected + "' içermiyor: " + current,
                current.contains(expected.toLowerCase())
        );
        // test sonunda istersen kapatıp ana pencereye dönebilirsin:
        // driver.close();
        // driver.switchTo().window(mainWindow);
    }
}
