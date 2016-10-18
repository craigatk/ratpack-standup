package standup.geb.multi

import geb.Browser
import geb.report.ReporterSupport
import geb.spock.GebReportingSpec

/**
 * Adapted from https://github.com/alxndrsn/geb-multibrowser/blob/master/MultiBrowserGebSpec.groovy
 */
class MultiBrowserGebSpec extends GebReportingSpec {
    private Map<String, Browser> browserMap = [:]

    private Browser overrideBrowser = null

    // A method to get the base URL from the test class instead of Geb config (if useful)
    String getBaseUrl() {
        null
    }

    /**
     * Run Geb commands in a separate browser window, creating it if necessary.
     *
     * @param browserId user-specified identifier string to reference this browser instance. If a browser with this ID
     *                  was used previously in the same test, will re-use it. Otherwise, will create a new browser.
     * @param c Closure with the code you want to execute in the separate browser.
     */
    void withBrowserSession(String browserId, Closure c) {
        if (!browserMap[browserId]) {
            Browser browser = createBrowser()
            // Disable browser caching so it will create another browser
            browser.config.cacheDriver = false

            String appBaseUrl = getBaseUrl()

            if (appBaseUrl) {
                browser.baseUrl = appBaseUrl
            }

            browserMap[browserId] = browser
        }

        overrideBrowser = browserMap[browserId]

        c.call()

        overrideBrowser = null
    }

    @Override
    Browser getBrowser() {
        overrideBrowser ?: super.getBrowser()
    }

    @Override
    void resetBrowser() {
        super.resetBrowser()

        browserMap.each { browserId, browserFromMap ->
            if (browserFromMap?.config?.autoClearCookies) {
                browserFromMap.clearCookiesQuietly()
            }
            // Only cached browsers are automatically closed, so close all the non-cached browsers by hand
            browserFromMap.close()
        }

        browserMap.clear()
    }

    @Override
    void report(String label = "") {
        super.report(label)

        Class testClass = getClass()

        browserMap.each { browserId, browserFromMap ->
            browserFromMap.reportGroup(testClass)

            String reportLabel = (label) ? "${browserId}-${label}" : browserId

            String reportName = ReporterSupport.toTestReportLabel(1, 1, gebReportingSpecTestName.methodName, reportLabel)
            browserFromMap.report(reportName)
        }
    }
}
