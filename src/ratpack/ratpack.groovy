import groovy.json.JsonSlurper
import standup.Status

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

List<Status> statusStorage = []
JsonSlurper jsonSlurper = new JsonSlurper()

ratpack {
    handlers {
        prefix("api") {
            prefix("status") {
                post {
                    request.body.map { body ->
                        jsonSlurper.parseText(body.text) as Map
                    }.map { data ->
                        new Status(data)
                    }.then { status ->
                        statusStorage << status

                        response.send(toJson(status))
                    }
                }
                get("all") {
                    response.send(toJson(statusStorage))
                }
            }
        }
    }
}