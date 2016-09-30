import groovy.json.JsonSlurper
import groovy.sql.Sql
import org.h2.jdbcx.JdbcDataSource
import ratpack.groovy.sql.SqlModule
import ratpack.service.Service
import ratpack.service.StartEvent
import standup.DefaultStatusService
import standup.Status
import standup.StatusService

import javax.sql.DataSource

import static groovy.json.JsonOutput.toJson
import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        module SqlModule
        bindInstance(JsonSlurper, new JsonSlurper())
        bindInstance(DataSource, new JdbcDataSource(
                URL: "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1",
                user: "sa",
                password: ""
        ))
        bind(StatusService, DefaultStatusService)
        bindInstance(new Service() {
            void onStart(StartEvent e) {
                Sql sql = e.registry.get(Sql)
                sql.execute(
                        "CREATE TABLE status_item(id INT PRIMARY KEY AUTO_INCREMENT, " +
                                "name VARCHAR(255), " +
                                "completed_yesterday VARCHAR(255), " +
                                "working_on_today VARCHAR(255), " +
                                "impediments VARCHAR(255), " +
                                "date_created TIMESTAMP" +
                                ")"
                )
            }
        })
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
        /*files {
            dir("static").indexFiles("index.html")
        }*/
    }
}