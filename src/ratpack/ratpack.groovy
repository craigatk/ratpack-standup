import groovy.json.JsonSlurper
import org.h2.jdbcx.JdbcDataSource
import standup.DatabaseMigrationService
import standup.DefaultStatusService
import standup.StatusService
import standup.handler.CreateStatusHandler
import standup.handler.GetAllStatusHandler

import javax.sql.DataSource

import static ratpack.groovy.Groovy.ratpack

ratpack {
    bindings {
        bindInstance(DataSource, new JdbcDataSource(
                URL: "jdbc:h2:mem:test${System.getProperty('db', '0')};DB_CLOSE_DELAY=-1",
                user: "sa",
                password: ""
        ))
        bindInstance(new DatabaseMigrationService())
        bindInstance(JsonSlurper, new JsonSlurper())
        bind(StatusService, DefaultStatusService)
        bind(CreateStatusHandler)
        bind(GetAllStatusHandler)
    }
    handlers {
        prefix("api") {
            prefix("status") {
                post(CreateStatusHandler)
                get("all", GetAllStatusHandler)
            }
        }
        files {
            dir("static").indexFiles("index.html")
        }
    }
}