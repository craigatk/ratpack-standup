package standup.handler

import groovy.json.JsonSlurper
import ratpack.handling.Context
import ratpack.handling.Handler
import standup.Status
import standup.StatusService

import javax.inject.Inject

import static groovy.json.JsonOutput.toJson

class CreateStatusHandler implements Handler {

    private final JsonSlurper jsonSlurper
    private final StatusService statusService

    @Inject
    CreateStatusHandler(JsonSlurper jsonSlurper, StatusService statusService) {
        this.jsonSlurper = jsonSlurper
        this.statusService = statusService
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
            ctx.response.send(toJson(status))
        }
    }
}
