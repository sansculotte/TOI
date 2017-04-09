package com.io.toui.test.udp;

import com.io.toui.model.*;

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

    private final Collection<InetAddress> clients = new ArrayList<>();

    private int targetPort = 8182;

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

                if (!clients.contains(receivePacket.getAddress())) {
                    // FIXME: bad idea to store those "clients" - we never know when they go away
                    // add client anyway - ignore advice and go on
                    clients.add(receivePacket.getAddress());
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
            System.err.println("no clients " + new String(_data));

            return;
        }

        clients.forEach(_inetAddress -> {

            System.out.println("ip: " + _inetAddress.getHostAddress() + ":" + targetPort + " :: " +
                               "" + new String
                    (_data));

            final DatagramPacket sendPacket = new DatagramPacket(_data,
                                                                 _data.length,
                                                                 _inetAddress,
                                                                 targetPort);

            try {
                socket.send(sendPacket);
            }
            catch (final IOException _e) {
                _e.printStackTrace();
            }

        });
    }

    @Override
    public void send(final Packet<?> _packet) {

    }

    @Override
    public void setListener(final ITransporterListener _listener) {

        listener = _listener;
    }

    @Override
    public void setSerializer(final Class<ITOUISerializer> _serializerClass) {

    }

    @Override
    public Class<ITOUISerializer> getSerializer() {
        return null;
    }

    @Override
    public void received(final byte[] _data) {

        if (listener != null) {
            listener.received(_data);
        }
    }

    public void setTargetPort(final int _targetPort) {

        targetPort = _targetPort;
    }

}
