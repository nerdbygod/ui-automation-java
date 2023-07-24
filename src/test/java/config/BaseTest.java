package config;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;

import static com.codeborne.selenide.Selenide.*;

public abstract class BaseTest {
    public static void setUp() {
        WebDriverManager.chromedriver().setup();

        Configuration.browser = "chrome";
        Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "2880x1800";
        Configuration.headless = false;
        Configuration.baseUrl = "https://demoqa.com/";
//        Configuration.holdBrowserOpen = true;

    }

    @BeforeAll
    public static void init() {
        setUp();
    }

    @BeforeEach
    public void addAdblockExt() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("enable-automation");
        chromeOptions.addArguments("--no-sandbox");
        chromeOptions.addArguments("--disable-gpu");
        chromeOptions.addArguments("disable-features=NetworkService");
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.EAGER);
        chromeOptions.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
//        chromeOptions.addExtensions(new File("/Users/nerdbygod/JavaProjects/ui-automation-java/adblockExt/adblock.crx"));
        Configuration.browserCapabilities = new DesiredCapabilities();
        Configuration.browserCapabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
    }

    public void removeAds() {
        SelenideElement bottomAd = $("#adplus-anchor");
        SelenideElement rightSideAd = $("#RightSide_Advertisement");
        SelenideElement arrow = $("#close-fixedban");
        executeJavaScript("""
                arguments[0].remove();\s
                arguments[1].remove();\s
                arguments[2].remove();
                """, rightSideAd, bottomAd, arrow);
    }
}