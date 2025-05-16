package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.DriverUtils;
import java.util.List;

public class FooterPage {
    public FooterPage() {
        PageFactory.initElements(DriverUtils.getDriver(), this);
    }

    @FindBy(xpath = "//div[contains(@class,'style_footerItems__wuNdf')]")
    public List<WebElement> footerLinks;

    // Social media icons (each is an <a> element)
    @FindBy(xpath = "//div[contains(@class,'style_footerSocialMediaIconContainer__Bp9ct')]//a[contains(@href,'facebook.com')]")
    public WebElement facebookIcon;

    @FindBy(xpath = "//div[contains(@class,'style_footerSocialMediaIconContainer__Bp9ct')]//a[contains(@href,'linkedin.com')]")
    public WebElement linkedinIcon;

    @FindBy(xpath = "//div[contains(@class,'style_footerSocialMediaIconContainer__Bp9ct')]//a[contains(@href,'twitter.com')]")
    public WebElement twitterIcon;

    @FindBy(xpath = "//div[contains(@class,'style_footerSocialMediaIconContainer__Bp9ct')]//a[contains(@href,'instagram.com')]")
    public WebElement instagramIcon;
}
