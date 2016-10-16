package standup.geb.multi

import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import standup.geb.HomePage

class StandupMultiBrowserGebSpec extends MultiBrowserGebSpec {
    @AutoCleanup
    def aut = new GroovyRatpackMainApplicationUnderTest()

    def setup() {
        URI base = aut.address
        browser.baseUrl = base.toString()
    }

    @Override
    String getBaseUrl() {
        aut.address.toString()
    }

    void 'when submitted status in a separate browser window should appear in both browsers'() {
        given:
        HomePage homePageFirstBrowser = to(HomePage)

        when:
        withBrowserSession('second') {
            HomePage homePageSecondBrowser = to(HomePage)
            homePageSecondBrowser.submitStatus('Second browser')
        }

        then:
        waitFor { homePageFirstBrowser.findStatusFor('Second browser') }

        when:
        homePageFirstBrowser.submitStatus('First browser')

        then:
        withBrowserSession('second') {
            HomePage homePageSecondBrowser = browser.page
            waitFor { homePageSecondBrowser.findStatusFor('First browser') }
        }
    }
}
