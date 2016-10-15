package standup.handler

import groovy.json.JsonSlurper
import ratpack.handling.Context
import ratpack.handling.Handler
import standup.Status
import standup.StatusBroadcaster
import standup.StatusService

import javax.inject.Inject

import static groovy.json.JsonOutput.toJson

class CreateStatusHandler implements Handler {

    private final JsonSlurper jsonSlurper
    private final StatusService statusService
    private final StatusBroadcaster statusBroadcaster

    @Inject
    CreateStatusHandler(JsonSlurper jsonSlurper, StatusService statusService, StatusBroadcaster statusBroadcaster) {
        this.jsonSlurper = jsonSlurper
        this.statusService = statusService
        this.statusBroadcaster = statusBroadcaster
    }

    @Override
    void handle(Context ctx) {
        ctx.request.body.map { body ->
            jsonSlurper.parseText(body.text) as Map
        }.map { data ->
            new Status(data)
        }.flatMap { status ->
            statusService.create(status)
        }.then { status ->
            statusBroadcaster.sendMessage(status)

            ctx.response.send(toJson(status))
        }
    }
}
