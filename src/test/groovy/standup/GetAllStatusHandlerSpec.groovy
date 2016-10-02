package standup

import groovy.json.JsonSlurper
import ratpack.exec.Promise
import ratpack.handling.Handler
import ratpack.test.handling.RequestFixture
import spock.lang.Specification
import standup.handler.GetAllStatusHandler

class GetAllStatusHandlerSpec extends Specification {
    StatusService statusService = Mock()

    Handler handler = new GetAllStatusHandler(statusService)

    RequestFixture requestFixture = RequestFixture.requestFixture()

    void "should get all status items"() {
        given:
        Status status = new Status(name: 'My Name', yesterday: 'Completed yesterday')

        when:
        def result = requestFixture.handle(handler)

        then:
        1 * statusService.list() >> Promise.sync { [status] }

        and:
        assert result.status.code == 200

        and:
        def parsedJson = new JsonSlurper().parseText(result.bodyText)
        assert parsedJson.size() == 1
        assert parsedJson[0].name == status.name
        assert parsedJson[0].yesterday == status.yesterday
    }
}
