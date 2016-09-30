package standup

import org.skife.jdbi.v2.DBI
import ratpack.exec.Blocking
import ratpack.exec.Promise

import javax.inject.Inject
import javax.sql.DataSource

class DefaultStatusService implements StatusService {
    private final DBI dbi

    @Inject
    DefaultStatusService(DataSource dataSource) {
        assert dataSource

        this.dbi = new DBI(dataSource)
    }

    @Override
    Promise<Status> create(Status newStatus) {
        Blocking.get {
            StatusDAO statusDAO = dbi.open(StatusDAO)

            statusDAO.create(newStatus.name, newStatus.yesterday, newStatus.today, newStatus.impediments)

            return newStatus
        }
    }

    @Override
    Promise<List<Status>> list() {
        return Blocking.get {
            StatusDAO statusDAO = dbi.open(StatusDAO)

            return statusDAO.list()
        }
    }
}
