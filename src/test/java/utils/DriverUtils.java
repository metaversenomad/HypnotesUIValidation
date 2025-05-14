package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverUtils {

    // Thread-local WebDriver instance
    private static InheritableThreadLocal<WebDriver> driverPool = new InheritableThreadLocal<>();

    /**
     * Returns the WebDriver instance for the current thread.
     * If no WebDriver instance exists, creates a new one based on the browser type.
     * @return WebDriver instance
     */
    public static WebDriver getDriver() {

        // If thread-local driver does not exist, a new one will be created
        if (driverPool.get() == null) {
            String browserType = ConfigReader.getConfigProperty("browser").toLowerCase();

            switch (browserType) {
                case "chrome":
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--remote-allow-origins=*");
                    driverPool.set(new ChromeDriver(options));
                    break;

                case "firefox":
                    driverPool.set(new FirefoxDriver());
                    break;

                default:
                    throw new RuntimeException("Invalid browser type: " + browserType);
            }

            // Timeout (implicit wait) for WebDriver and maximize the window
            driverPool.get().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driverPool.get().manage().window().maximize();
        }

        return driverPool.get();
    }

    /**
     * Closes the WebDriver instance for the current thread.
     */
    public static void closeDriver() {
        if (driverPool.get() != null) {
            driverPool.get().quit();
            driverPool.remove();
        }
    }
}
