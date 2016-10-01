package standup

import geb.spock.GebReportingSpec
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup

class StandupGebSpec extends GebReportingSpec {
    @AutoCleanup
    def aut = new GroovyRatpackMainApplicationUnderTest()

    def setup() {
        URI base = aut.address
        browser.baseUrl = base.toString()
    }

    void "should submit status"() {
        given:
        HomePage homePage = to HomePage

        String name = 'Craig Atkinson'
        String yesterday = 'Completed that important task'
        String today = 'Investigating production issue'

        when:
        homePage.submitStatus(name, yesterday, today)

        then:
        waitFor { homePage.numberOfStatusDisplays == 1 }

        and:
        StatusDisplayModule myStatus = homePage.findStatusFor(name)
        assert myStatus?.yesterday == yesterday
        assert myStatus.today == today
    }
}
