import io.github.bonigarcia.wdm.ChromeDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

reportsDir = "build/geb"

driver = {
    // Download and configure ChromeDriver using https://github.com/bonigarcia/webdrivermanager
    ChromeDriverManager.getInstance().setup()

    new ChromeDriver()
}

environments {
    // run with "gradlew -Dgeb.env=firefox testBrowser"
    firefox {
        driver = { new FirefoxDriver() }
    }
}