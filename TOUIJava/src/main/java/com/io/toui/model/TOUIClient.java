package com.io.toui.model;

import com.io.toui.model.ICommands.Add;
import com.io.toui.model.ICommands.Remove;

/**
 * Created by inx on 30/11/16.
 */
public class TOUIClient extends TOUIBase {

    // callback objects
    private Add addListener;

    private Remove removeListener;

    //------------------------------------------------------------
    //
    public TOUIClient(final ITOUISerializer _ser, final ITransporter _trans) {
        super(_ser, _trans);
    }

    //------------------------------------------------------------
    //
    public void setAddListener(final Add _listener) {

        addListener = _listener;
    }

    public void setRemoveListener(final Remove _listener) {

        removeListener = _listener;
    }

    //------------------------------------------------------------
    //

    /**
     * send init to server
     */
    public void init() {

        // clear cache?
        valueCache.clear();

        if (transporter != null) {
            // send to all clients
            final Packet packet = new Packet(ICommands.INIT, null, 0, null);
            transporter.send(serializer.serialize(packet));
        }
    }

    //------------------------------------------------------------
    //
    @Override
    public void received(final byte[] _data) {

        // deserialize
        final Packet aPacket = serializer.deserialize(_data);

        if (aPacket != null) {

            if (aPacket.command.equals(ICommands.ADD) ||
                aPacket.command.equals(ICommands.UPDATE) ||
                aPacket.command.equals(ICommands.REMOVE))
            {

                _update(aPacket);
            }
            else if (aPacket.command.equals(ICommands.VERSION)) {

                // try to convert to version object
                System.out.println("version object yet to be specified");
            }
            else {

                System.err.println("not implemented command: " + aPacket.command);
            }
        }
        else {

            System.err.println("no packet: " + new String(_data));
        }
    }


    private void _update(final Packet _packet) {

        // try to convert data to ValueDescription
        try {

            final ValueDescription<?> val = serializer.convertToValueDescription(_packet.data);

            switch (_packet.command) {
                case ICommands.ADD:
                    if (addListener != null) {
                        addListener.add(val);
                    }
                    break;

                case ICommands.REMOVE:
                    if (removeListener != null) {
                        removeListener.remove(val);
                    }
                    break;

                case ICommands.UPDATE:
                    if (updateListener != null) {
                        updateListener.update(val);
                    }

                    // TODO: should call own update here?
                    // -> this should update all clients...
                    // optimize, do not send back to same client??

                    break;

                default:
                    System.err.println("no such command: " + _packet.command);
            }

        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
