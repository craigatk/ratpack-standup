package standup

import groovy.transform.Canonical
import org.skife.jdbi.v2.StatementContext
import org.skife.jdbi.v2.tweak.ResultSetMapper

import java.sql.ResultSet

@Canonical
class Status {
    String name
    String yesterday
    String today
    String impediments
    Date dateCreated

    static class Mapper implements ResultSetMapper<Status> {
        @Override
        Status map(int idx, ResultSet rs, StatementContext sc) {
            return new Status(
                    name: rs.getString('name'),
                    yesterday: rs.getString('completed_yesterday'),
                    today: rs.getString('working_on_today'),
                    impediments: rs.getString('impediments'),
                    dateCreated: rs.getDate('date_created')
            )
        }
    }
}
