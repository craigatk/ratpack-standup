/* global WebSocket,location */

import store from '../store'

let webSocket

let initWebsocket = () => {
  if (!webSocket || webSocket.readyState !== WebSocket.OPEN) {
    // During development the Ratpack server is on port 5050
    let severPort = location.port === '8080' ? '5050' : location.port

    webSocket = new WebSocket('ws://' + location.hostname + ':' + severPort + '/ws/status')

    webSocket.onopen = (event) => {
      console.log('WebSocket opened!')
    }

    webSocket.onmessage = (event) => {
      let newStatus = JSON.parse(event.data)

      store.addStatus(newStatus)
    }

    webSocket.onclose = (event) => {
      var timer = setTimeout(function () {
        console.log('Retrying WebSocket connection...')
        initWebsocket()

        if (webSocket.readyState === WebSocket.OPEN) {
          clearTimeout(timer)
        }
      }, 1000)
    }
  }
}

export default initWebsocket
