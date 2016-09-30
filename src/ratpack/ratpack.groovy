import groovy.json.JsonSlurper
import standup.DefaultStatusService
import standup.Status
import standup.StatusService

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        bindInstance(StatusService, new DefaultStatusService())
        bindInstance(JsonSlurper, new JsonSlurper())
    }
    handlers {
        prefix("api") {
            prefix("status") {
                post { JsonSlurper jsonSlurper, StatusService statusService ->
                    request.body.map { body ->
                        jsonSlurper.parseText(body.text) as Map
                    }.map { data ->
                        new Status(data)
                    }.flatMap { status ->
                        statusService.create(status)
                    }.then { status ->
                        response.send(toJson(status))
                    }
                }
                get("all") { StatusService statusService ->
                    statusService.list().then { statusList ->
                        response.send(toJson(statusList))
                    }
                }
            }
        }
    }
}