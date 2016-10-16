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
        if (overrideBrowser) {
            return overrideBrowser
        } else {
            return super.getBrowser()
        }
    }

    @Override
    void resetBrowser() {
        super.resetBrowser()

        browserMap.each { browserId, browserFromMap ->
            if (browserFromMap?.config?.autoClearCookies) {
                browserFromMap.clearCookiesQuietly()
            }
            // Only cached browsers are automatically closed, so close the non-cached browsers by hand
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
