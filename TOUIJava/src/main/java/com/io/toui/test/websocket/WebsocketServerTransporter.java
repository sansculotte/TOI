package com.io.toui.test.websocket;

import com.io.toui.model.ITransporter;
import com.io.toui.model.ITransporterListener;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Created by inx on 28/12/16.
 */
public class WebsocketServerTransporter extends WebSocketServer implements ITransporter {

//    static {
//        WebSocketImpl.DEBUG = true;
//    }

    //------------------------------------------------
    private ITransporterListener listener;

    private final ArrayList<WebSocket> clients = new ArrayList<>();

    public WebsocketServerTransporter(final int port) {
        super(new InetSocketAddress(port));
        start();
    }

    public WebsocketServerTransporter(final InetSocketAddress address) {
        super(address);
        start();
    }


    //------------------------------------------------
    //------------------------------------------------
    // WebSocketServer
    @Override
    public void onOpen(final WebSocket _socket, final ClientHandshake arg1) {

        System.out.println("opened");
        clients.add(_socket);
    }

    @Override
    public void onClose(final WebSocket _socket, final int code, final String reason, final boolean remote) {
        System.out.println("closed");
        clients.remove(_socket);
    }

    @Override
    public void onError(final WebSocket _socket, final Exception _ex) {
        System.out.println("error, remote port: " + _socket.getRemoteSocketAddress()
                                                                    .getPort());
        _ex.printStackTrace();
    }

    @Override
    public void onMessage(final WebSocket _socket, final String _message) {

        System.out.println("received: " + _message);
        received(_message.getBytes());
    }

    @Override
    public void onMessage(final WebSocket conn, final ByteBuffer message) {
        System.out.println("received bytes: ");
        received(message.array());
    }

    //------------------------------------------------
    //------------------------------------------------
    // ITransporter
    @Override
    public void received(final byte[] _data) {
        if (listener != null) {
            listener.received(_data);
        }
    }

    @Override
    public void send(final byte[] _data) {

        if (_data == null) {
            return;
        }

        if (clients.isEmpty()) {
//            System.err.println("no clients");
            return;
        }

        clients.forEach(_webSocket -> {
            System.out.println("sending to: " + _webSocket.getRemoteSocketAddress().getHostString
                    () + ":" + _webSocket.getRemoteSocketAddress().getPort());
//            _webSocket.send(new String(_data));
            _webSocket.send(_data);
        });
    }

    @Override
    public void setListener(final ITransporterListener _listener) {
        listener = _listener;
    }
}
