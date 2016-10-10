package standup.geb.parallel

import standup.geb.StandupGebSpec

class Standup2GebSpec extends StandupGebSpec {
    protected String getTheName() {
        'Kate Beckette'
    }

    protected String getTheYesterday() {
        'Closed a case'
    }

    protected String getTheToday() {
        'Starting a new one'
    }
}
