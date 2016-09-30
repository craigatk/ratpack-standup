package standup

import groovy.transform.Canonical

@Canonical
class Status {
    String name
    String yesterday
    String today
    String impediments
}
