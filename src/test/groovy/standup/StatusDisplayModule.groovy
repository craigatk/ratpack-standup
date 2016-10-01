package standup

import geb.Module

class StatusDisplayModule extends Module {
    static content = {
        nameSection { $(".name") }
        yesterdaySection { $(".yesterday") }
        todaySection { $(".today") }
        impedimentsSection { $(".impediments") }
    }

    String getName() {
        nameSection.text()
    }

    String getYesterday() {
        yesterdaySection.text()
    }

    String getToday() {
        todaySection.text()
    }

    String getImpediments() {
        impedimentsSection.text()
    }
}
