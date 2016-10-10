import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

reportsDir = "build/geb"

driver = {
    def theDriver = new FirefoxDriver()

    theDriver.manage().window().setSize(new Dimension(500, 900))

    return theDriver
}

environments {

    // run with "gradlew -Dgeb.env=chrome testBrowser"
    // ChromeDriver reference: https://sites.google.com/a/chromium.org/chromedriver/
    chrome {
        // Download and configure ChromeDriver using https://github.com/bonigarcia/webdrivermanager
        ChromeDriverManager.getInstance().setup()

        driver = { new ChromeDriver() }
    }
}