package standup

import groovy.util.logging.Slf4j
import ratpack.handling.Context
import ratpack.websocket.WebSocket
import ratpack.websocket.WebSockets

import java.util.concurrent.CopyOnWriteArrayList

import static groovy.json.JsonOutput.toJson

@Slf4j
class StatusBroadcaster {
  private final List<WebSocket> listeners = new CopyOnWriteArrayList<>()

  public void register(Context context) {
    WebSockets.websocket(context) { ws ->
      log.info("registering WebSocket context")

      listeners << ws

      return ws
    } connect { ws ->
      ws.onClose {
        log.info("Removing WebSocket")
        listeners.remove(it.openResult)
      }
    }
  }

  public void sendMessage(Status status) {
    log.info("Sending messages ${status} to ${listeners.size()} listeners")

    listeners.each { WebSocket ws ->
      log.info('sending message to websocket')
      ws.send(toJson(status))
    }
  }
}
