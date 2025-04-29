package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

    @FindBy(css = "footer") // örnek
    public WebElement footer;

    // Ek footer item'lar burada tanımlanacak
}
