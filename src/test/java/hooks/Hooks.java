package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;
import utils.DriverUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Cucumber hooks to set up and tear down the WebDriver for each scenario.
 */
public class Hooks {

    private static final Map<String, String> CONTEXT = new HashMap<>();
    private WebDriver driver;

    @Before
    public void setup(Scenario scenario) {
        // 1) Initialize WebDriver
        driver = DriverUtils.getDriver();

        // 2) Store the main window handle for later
        String mainHandle = driver.getWindowHandle();
        CONTEXT.put("MAIN_WINDOW", mainHandle);
        scenario.log("Main window handle: " + mainHandle);

        // 3) Navigate to the application’s homepage from configuration
        String baseUrl = ConfigReader.getProperty("url");
        driver.get(baseUrl);

        // 4) Configure browser window and timeouts
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void teardown(Scenario scenario) {
        // 1) Always switch back to the main window first
        String mainHandle = CONTEXT.get("MAIN_WINDOW");
        if (mainHandle != null) {
            try {
                driver.switchTo().window(mainHandle);
            } catch (Exception ignored) {
                // If already closed or invalid, ignore
            }
        }

        // 2) If the scenario failed, take and attach a screenshot
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", scenario.getName());
            } catch (Exception e) {
                scenario.log("Could not capture screenshot: " + e.getMessage());
            }
        }

        // 3) Quit the WebDriver to clean up
        DriverUtils.closeDriver();
    }

    /**
     * Provides access to the main window handle from step definitions.
     *
     * @return the handle of the original browser window
     */
    public static String getMainWindowHandle() {
        return CONTEXT.get("MAIN_WINDOW");
    }
}
