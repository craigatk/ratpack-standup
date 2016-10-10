package standup.geb

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
        //sleep(200)
        yesterdayField.value(yesterday)
        //sleep(200)
        todayField.value(today)
        //sleep(200)
        impedimentsField.value(impediments)
        //sleep(200)

        submitButton.click()
    }

    boolean hasStatusFor(String name) {
        statusDisplayList.any { it.name == name }
    }

    StatusDisplayModule findStatusFor(String name) {
        statusDisplayList.find { it.name == name }
    }
}
