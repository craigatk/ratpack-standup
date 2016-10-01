package standup

import geb.Page

class HomePage extends Page {
    static url = "/"

    static content = {
        nameField { $("#yourName") }
        yesterdayField { $("#yesterday") }
        todayField { $("#today") }
        impedimentsField { $("#impediments") }
        submitButton { $("#submit") }

        statusDisplayList { $(".status-display").moduleList(StatusDisplayModule) }
    }

    void submitStatus(String name, String yesterday, String today, String impediments = "") {
        nameField.value(name)
        yesterdayField.value(yesterday)
        todayField.value(today)
        impedimentsField.value(impediments)

        submitButton.click()
    }

    List<Status> allStatusDisplays() {
        statusDisplayList.collect { it.status }
    }

    Status findStatusFor(String name) {
        statusDisplayList.find { it.status?.name == name }?.status
    }
}
