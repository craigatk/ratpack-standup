package standup.geb

import geb.spock.GebReportingSpec
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup

import static groovy.json.JsonOutput.toJson

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

    void "when new status added should update existing users via WebSocket"() {
        when:
        HomePage homePage = to HomePage

        then:
        waitFor { homePage.numberOfStatusDisplays == 0 }

        when: 'adding a new status using the JSON REST API'
        aut.httpClient.requestSpec { spec ->
            spec.body { b ->
                b.text(toJson([name: 'WebSocket Test']))
            }
        }.post("api/status")

        then:
        waitFor { homePage.numberOfStatusDisplays == 1 }

        assert homePage.findStatusFor('WebSocket Test')
    }
}
