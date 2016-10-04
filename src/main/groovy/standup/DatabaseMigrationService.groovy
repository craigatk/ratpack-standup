package standup

import org.flywaydb.core.Flyway
import ratpack.service.Service
import ratpack.service.StartEvent

import javax.sql.DataSource

class DatabaseMigrationService implements Service {
    void onStart(StartEvent e) {
        DataSource dataSource = e.registry.get(DataSource)

        Flyway flyway = new Flyway()

        flyway.setDataSource(dataSource)

        flyway.migrate()
    }
}
