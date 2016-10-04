import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

reportsDir = "build/geb"

driver = { new FirefoxDriver() }

environments {

    // run with "gradlew -Dgeb.env=chrome testBrowser"
    // ChromeDriver reference: https://sites.google.com/a/chromium.org/chromedriver/
    chrome {
        // Download and configure ChromeDriver using https://github.com/bonigarcia/webdrivermanager
        ChromeDriverManager.getInstance().setup()

        driver = { new ChromeDriver() }
    }
}