package standup.geb

import geb.spock.GebReportingSpec
import org.apache.commons.lang3.RandomStringUtils
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Unroll

class StandupGebSpec extends GebReportingSpec {
    @AutoCleanup
    def aut

    def setup() {
        System.setProperty('db', RandomStringUtils.randomNumeric(8))
        aut = new GroovyRatpackMainApplicationUnderTest()

        URI base = aut.address
        browser.baseUrl = base.toString()
    }

    @Unroll
    void "should submit status #iteration"() {
        given:
        HomePage homePage = to HomePage

        String name = theName + ' ' + iteration
        String yesterday = theYesterday + ' ' + iteration
        String today = theToday + ' ' + iteration

        when:
        homePage.submitStatus(name, yesterday, today)

        then:
        waitFor { homePage.hasStatusFor(name) }

        and:
        StatusDisplayModule myStatus = homePage.findStatusFor(name)
        assert myStatus?.yesterday == yesterday
        assert myStatus.today == today

        where:
        iteration << ['1', '2', '3', '4', '5', '6']
    }

    protected String getTheName() {
        'Craig Atkinson'
    }

    protected String getTheYesterday() {
        'Completed that important task'
    }

    protected String getTheToday() {
        'Investigating production issue'
    }
}
