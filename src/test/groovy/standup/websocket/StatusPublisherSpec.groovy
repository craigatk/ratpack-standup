package standup.websocket

import groovy.json.JsonSlurper
import ratpack.groovy.test.GroovyRatpackMainApplicationUnderTest
import spock.lang.AutoCleanup
import spock.lang.Specification
import standup.Status

import java.util.concurrent.TimeUnit

import static groovy.json.JsonOutput.toJson

class StatusPublisherSpec extends Specification {
    @AutoCleanup
    def aut = new GroovyRatpackMainApplicationUnderTest()

    JsonSlurper jsonSlurper = new JsonSlurper()

    void 'should broadcast new status to WebSocket client'() {
        given:
        Status newStatus = new Status(name: 'WebSocket Test', yesterday: 'Done yesterday', today: 'Working on today')

        TestWebSocketClient wsClient = openWsClient()
        wsClient.connectBlocking()

        when:
        aut.httpClient.requestSpec { spec ->
            spec.body { b ->
                b.text(toJson(newStatus))
            }
        }.post("api/status")

        then:
        def rawJson = wsClient.received.poll(2, TimeUnit.SECONDS)
        assert rawJson

        def parsedJson = jsonSlurper.parseText(rawJson)

        assert parsedJson.name == 'WebSocket Test'
        assert parsedJson.yesterday == 'Done yesterday'
        assert parsedJson.today == 'Working on today'

        cleanup:
        wsClient.closeBlocking()
    }

    private TestWebSocketClient openWsClient() {
        new TestWebSocketClient(new URI("ws://localhost:${aut.address.port}/ws/status"))
    }
}
