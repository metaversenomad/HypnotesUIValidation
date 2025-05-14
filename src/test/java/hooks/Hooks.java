package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.DriverUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private static final Map<String, String> CONTEXT = new HashMap<>();
    private WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        // 1) Initialize the WebDriver
        driver = DriverUtils.getDriver();

        // 2) Store the main window handle
        String mainHandle = driver.getWindowHandle();
        CONTEXT.put("MAIN_WINDOW", mainHandle);
        scenario.log("Main window handle: " + mainHandle);

        // 3) Navigate to the application’s homepage
        driver.get("https://hypnotes.net");

        // 4) Configure browser settings
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void teardown(Scenario scenario) {
        // Always switch back to the main window first
        String mainHandle = CONTEXT.get("MAIN_WINDOW");
        if (mainHandle != null) {
            try {
                driver.switchTo().window(mainHandle);
            } catch (Exception ignored) {
                // If it's already closed or invalid, ignore
            }
        }

        // If the scenario failed, take a screenshot
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                // Even if we can’t capture a screenshot, continue cleaning up
                scenario.log("Could not capture screenshot: " + e.getMessage());
            }
        }

        // Finally, quit the WebDriver completely
        DriverUtils.closeDriver();
    }

    /**
     * Provides access to the main window handle from step definitions
     */
    public static String getMainWindowHandle() {
        return CONTEXT.get("MAIN_WINDOW");
    }
}
