package standup.websocket

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

import java.util.concurrent.LinkedBlockingQueue

/**
 * Adapted from https://github.com/danveloper/ratpack-foaas/blob/master/src/test/groovy/app/WebSocketsSpec.groovy
 */
class TestWebSocketClient extends WebSocketClient {
    final LinkedBlockingQueue<String> received = new LinkedBlockingQueue<String>()

    TestWebSocketClient(URI serverURI) {
        super(serverURI)
    }

    @Override
    void onOpen(ServerHandshake handshakeData) { }

    @Override
    void onMessage(String message) {
        received.put message
    }

    @Override
    void onClose(int code, String reason, boolean remote) { }

    @Override
    void onError(Exception ex) { }
}
