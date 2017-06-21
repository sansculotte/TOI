package com.io.toui.test.udp;

import com.io.toui.model.*;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

/**
 * Created by inx on 30/11/16.
 */
public class UDPClientTransporter extends Thread implements ITransporter {

    //------------------------------------------------------------
    //
    private InetAddress address;

    private int port;

    private final DatagramSocket clientSocket;

    private volatile boolean doit = true;

    private ITransporterListener listener;

    //------------------------------------------------------------
    //
    public UDPClientTransporter(final String _address, final int _port) throws
                                                                        SocketException,
                                                                        UnknownHostException {

        clientSocket = new DatagramSocket();

        address = InetAddress.getByName(_address);
        port = _port;

        if (port < 0) {
            throw new RuntimeException("no port < 0");
        }

        start();
    }

    @Override
    public void run() {

        final byte[] receiveData = new byte[1024];

        while (doit) {

            final DatagramPacket receivePacket = new DatagramPacket(receiveData,
                                                                    receiveData.length);

            try {
                clientSocket.receive(receivePacket);

                received(Arrays.copyOf(receivePacket.getData(), receivePacket.getLength()));
            }
            catch (IOException _e) {
                _e.printStackTrace();
            }
        }

        clientSocket.close();
        System.out.println("finishing Client Transporter");
    }

    @Override
    public void send(final byte[] _data) {

        if (_data == null) {
            return;
        }

        final DatagramPacket sendPacket = new DatagramPacket(_data, _data.length, address, port);

        try {
            clientSocket.send(sendPacket);
        }
        catch (final IOException _e) {
            _e.printStackTrace();
        }
    }

    @Override
    public void send(final ToiPacket _packet) {
        // TODO
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

    @Override
    public void received(final ToiPacket _packet) {
        if (listener != null) {
            listener.received(_packet);
        }
    }
}
