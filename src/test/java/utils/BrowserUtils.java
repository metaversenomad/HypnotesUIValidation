package utils;

import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverUtils;

import java.time.Duration;
import java.util.Set;

public class BrowserUtils {

    /**
     * Pauses execution for the given number of seconds.
     */
    public static void waitFor(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Opens the given URL in a new tab, switches to it,
     * waits until current URL contains expectedPart, asserts it,
     * then closes that tab and switches back to mainHandle.
     *
     * @param urlToOpen     the href to open
     * @param expectedPart  substring expected in the final URL
     * @param mainHandle    window handle of the original tab
     */
    public static void openUrlInNewTabAndAssert(String urlToOpen, String expectedPart, String mainHandle) {
        WebDriver driver = DriverUtils.getDriver();
        Set<String> before = driver.getWindowHandles();

        ((JavascriptExecutor) driver)
                .executeScript("window.open(arguments[0], '_blank');", urlToOpen);

        // switch
        Set<String> after = driver.getWindowHandles();
        after.removeAll(before);
        String newHandle = after.iterator().next();
        driver.switchTo().window(newHandle);

        // wait & assert
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains(expectedPart));
        Assert.assertTrue(
                "Expected URL to contain '" + expectedPart + "', but was: " + driver.getCurrentUrl(),
                driver.getCurrentUrl().toLowerCase().contains(expectedPart.toLowerCase())
        );

        // teardown
        driver.close();
        driver.switchTo().window(mainHandle);
    }

}
