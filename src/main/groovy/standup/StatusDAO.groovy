package standup

import org.skife.jdbi.v2.sqlobject.Bind
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys
import org.skife.jdbi.v2.sqlobject.SqlQuery
import org.skife.jdbi.v2.sqlobject.SqlUpdate
import org.skife.jdbi.v2.sqlobject.customizers.Mapper

interface StatusDAO {
    @SqlUpdate("INSERT INTO status_item(name, completed_yesterday, working_on_today, impediments, date_created) VALUES(:name, :yesterday, :doing_today, :impediments, current_timestamp())")
    @GetGeneratedKeys
    long create(@Bind("name") String name,
                @Bind("yesterday") String yesterday,
                @Bind("doing_today") String today,
                @Bind("impediments") String impediments
    )

    @SqlQuery("SELECT name, completed_yesterday, working_on_today, impediments, date_created FROM status_item ORDER BY date_created")
    @Mapper(Status.Mapper)
    List<Status> list()

    void close()
}
