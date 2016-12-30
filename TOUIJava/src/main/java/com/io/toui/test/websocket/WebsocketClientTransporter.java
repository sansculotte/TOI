package com.io.toui.test.websocket;

import com.io.toui.model.ITransporter;
import com.io.toui.model.ITransporterListener;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.framing.Framedata;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Created by inx on 29/12/16.
 */
public class WebsocketClientTransporter extends WebSocketClient implements ITransporter {


    private ITransporterListener listener;

    public WebsocketClientTransporter(final String host, final int port) throws
                                                                         URISyntaxException,
                                                                         InterruptedException {
        super(new URI("ws://" + host + ":" + port), new Draft_17());
        connectBlocking();
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    // WebSocketClient
    @Override
    public void onOpen(final ServerHandshake handshakedata) {
    }

    @Override
    public void onClose(final int code, final String reason, final boolean remote) {
        System.out.println("websocketClient close");
    }

    @Override
    public void onError(final Exception ex) {
        System.out.println("websocketClient error");
        ex.printStackTrace();
    }

    @Override
    public void onMessage(final String message) {
        received(message.getBytes());
    }

    @Override
    public void onMessage(final ByteBuffer bytes) {
        System.out.println("received bytes");
        received(bytes.array());
    }

    @Override
    public void send(final byte[] data) {
        // send as string
        send(new String(data));
    }

    //------------------------------------------------------------
    //------------------------------------------------------------
    // ITransporter
    @Override
    public void received(final byte[] _data) {
        if (listener != null) {
            listener.received(_data);
        }
    }

    @Override
    public void setListener(final ITransporterListener _listener) {
        listener = _listener;
    }

}
