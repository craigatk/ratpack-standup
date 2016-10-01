package standup

import geb.Module

class StatusDisplayModule extends Module {
    static content = {
        nameSection { $(".name") }
        yesterdaySection { $(".yesterday") }
        todaySection { $(".today") }
        impedimentsSection { $(".impediments") }
    }

    Status getStatus() {
        new Status(
                name: nameSection.text(),
                yesterday: yesterdaySection.text(),
                today: todaySection.text(),
                impediments: impedimentsSection.text()
        )
    }
}
