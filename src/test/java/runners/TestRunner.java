package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "html:target/cucumber-reports.html",
                "rerun:target/rerun.txt",
        },
        features = "src/test/resources/features",
        glue = {"step_definitions", "hooks"},
        dryRun = false,
        tags = "@smoke",
        publish = true
)
public class TestRunner {

}