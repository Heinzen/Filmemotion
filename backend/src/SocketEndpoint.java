import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class SocketEndpoint extends WebSocketServer {

    private Set<WebSocket> conns;

    public SocketEndpoint(int tcpPort) {
        super(new InetSocketAddress(tcpPort));
        conns = new HashSet<>();
    }

    @Override
    public void onStart() {
        System.out.println("Starting");
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        MessageParser.parseParameters(message);
        conn.send(Context.getResponseMessage());
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        if (conn != null) {
            conns.remove(conn);
        }
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }
}