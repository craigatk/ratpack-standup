package standup

import groovy.json.JsonSlurper
import ratpack.exec.Promise
import ratpack.handling.Handler
import ratpack.test.handling.RequestFixture
import spock.lang.Specification
import standup.handler.CreateStatusHandler

import static groovy.json.JsonOutput.toJson

class CreateStatusHandlerSpec extends Specification {
    StatusService statusService = Mock()
    JsonSlurper jsonSlurper = new JsonSlurper()
    StatusBroadcaster statusBroadcaster = Mock()

    Handler handler = new CreateStatusHandler(jsonSlurper, statusService, statusBroadcaster)

    RequestFixture requestFixture = RequestFixture.requestFixture()

    void "should create new status"() {
        given:
        Status statusToCreate = new Status(name: 'My Name')

        when:
        def result = requestFixture.body(toJson(statusToCreate), 'application/json').handle(handler)

        then:
        1 * statusService.create({Status newStatus ->
            assert newStatus.name == 'My Name'

            return true
        }) >> Promise.sync { statusToCreate }
        1 * statusBroadcaster.sendMessage(statusToCreate)

        and:
        assert result.status.code == 200

        and:
        def parsedJson = jsonSlurper.parseText(result.bodyText)
        assert parsedJson.name == 'My Name'
    }
}
