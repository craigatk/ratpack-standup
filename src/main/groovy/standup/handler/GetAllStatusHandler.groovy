package standup.handler

import ratpack.handling.Context
import ratpack.handling.Handler
import standup.StatusService

import javax.inject.Inject

import static groovy.json.JsonOutput.toJson

class GetAllStatusHandler implements Handler {

    private final StatusService statusService

    @Inject
    GetAllStatusHandler(StatusService statusService) {
        this.statusService = statusService
    }

    @Override
    void handle(Context ctx) {
        statusService.list().then { statusList ->
            ctx.response.send(toJson(statusList))
        }
    }
}
