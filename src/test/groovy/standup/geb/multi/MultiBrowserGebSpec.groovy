package standup.geb.multi

import geb.Browser
import geb.spock.GebReportingSpec

/**
 * Adapted from https://github.com/alxndrsn/geb-multibrowser/blob/master/MultiBrowserGebSpec.groovy
 */
class MultiBrowserGebSpec extends GebReportingSpec {
    private Map<String, Browser> browserMap = [:]

    private Browser overrideBrowser = null

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

        browserMap.values().each {
            if (it?.config?.autoClearCookies) {
                it.clearCookiesQuietly()
            }
            // Only cached browsers are automatically closed, so close the non-cached browsers by hand
            it.close()
        }

        browserMap.clear()
    }
}
