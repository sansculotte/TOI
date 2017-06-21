package com.io.toui.test;

import com.io.toui.model.ITransporterListener;
import com.io.toui.model.ToiPacket;
import com.io.toui.test.websocket.server.WebsocketServerTransporterNetty;

import javax.net.ssl.SSLException;
import java.security.cert.CertificateException;

/**
 * Created by inx on 14/06/17.
 */
public class Server implements ITransporterListener {

    public static void main(String[] args) throws
                                           CertificateException,
                                           InterruptedException,
                                           SSLException {

        new Server();
    }


    final WebsocketServerTransporterNetty transporter;

    private Server() throws CertificateException, InterruptedException, SSLException {

        transporter = new WebsocketServerTransporterNetty(10000);
        transporter.setListener(this);
    }

    @Override
    public void received(final byte[] _data) {

        System.out.println("recv: " + new String(_data));
        transporter.send("bla".getBytes());
    }

    @Override
    public void received(final ToiPacket _packet) {

    }
}
