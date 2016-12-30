package com.io.toui.test.udp;

import com.io.toui.model.ITransporter;
import com.io.toui.model.ITransporterListener;

import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * Created by inx on 30/11/16.
 */
public class UDPServerTransporter extends Thread implements ITransporter {

    private class UDPClient {

        public InetAddress address;

        public int port;

        private UDPClient(final InetAddress _address, final int _port) {

            address = _address;
            port = _port;
        }
    }

    //------------------------------------------------------------
    //
    private int port;

    private final DatagramSocket socket;

    private volatile boolean doit = true;

    private ITransporterListener listener;

    private final Map<String, UDPClient> clients = new HashMap<>();

    //------------------------------------------------------------
    //
    public UDPServerTransporter(final int _port) throws SocketException {

        port = _port;

        if (port < 0) {
            throw new RuntimeException("no port < 0");
        }

        socket = new DatagramSocket(port);

        start();
    }

    @Override
    public void run() {

        final byte[] receiveData = new byte[1024];

        while (doit) {
            final DatagramPacket receivePacket = new DatagramPacket(receiveData,
                                                                    receiveData.length);

            try {
                socket.receive(receivePacket);

                final String key = receivePacket.getAddress().toString() + ":" + receivePacket.getPort();

                if (!clients.containsKey(key)) {
                    // FIXME: bad idea to store those "clients" - we never know when they go away
                    // add client anyway - ignore advice and go on
                    clients.put(key, new UDPClient(receivePacket.getAddress(), receivePacket
                            .getPort()));
                }

                received(Arrays.copyOf(receivePacket.getData(), receivePacket.getLength()));
            }
            catch (final IOException _e) {
                _e.printStackTrace();
            }
        }

        socket.close();
        System.out.println("finishing Client Transporter");
    }

    @Override
    public void send(final byte[] _data) {

        if (_data == null) {
            return;
        }

        if (clients.isEmpty()) {
            System.err.println("no clients");
            return;
        }

        clients.forEach((_s, _udpClient) -> {

            final DatagramPacket sendPacket = new DatagramPacket(_data,
                                                                 _data.length,
                                                                 _udpClient.address,
                                                                 _udpClient.port);

            try {
                socket.send(sendPacket);
            }
            catch (final IOException _e) {
                _e.printStackTrace();
            }

        });
    }

    @Override
    public void setListener(final ITransporterListener _listener) {

        listener = _listener;
    }

    @Override
    public void received(final byte[] _data) {

        if (listener != null) {
            listener.received(_data);
        }
    }
}
