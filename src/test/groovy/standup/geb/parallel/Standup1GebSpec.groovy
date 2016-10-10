package standup.geb.parallel

import standup.geb.StandupGebSpec

class Standup1GebSpec extends StandupGebSpec {
    protected String getTheName() {
        'Richard Castle'
    }

    protected String getTheYesterday() {
        'Wrote a book chapter'
    }

    protected String getTheToday() {
        'Starting next chapter'
    }
}
