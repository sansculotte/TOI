package com.io.toui.model;

import com.io.toui.model.ICommands.Init;

/**
 * Created by inx on 30/11/16.
 */
public class TOUIServer extends TOUIBase {

    //------------------------------------------------------------
    // callback objects
    private Init initListener;

    //------------------------------------------------------------
    //
    public TOUIServer(final ITOUISerializer _ser, final ITransporter _trans) {

        super(_ser, _trans);
    }

    //------------------------------------------------------------
    //
    public void setInitListener(final Init _listener) {

        initListener = _listener;
    }

    //------------------------------------------------------------
    //
    public void add(final ValueDescription<?> _value) {

        if (!valueCache.containsKey(_value.id)) {
            // add
            valueCache.put(_value.id, _value);
        }
        else {
            System.out.println("already added value with this id - ignore");
        }

        if (transporter != null) {
            // send to all clients
            final Packet packet = new Packet(ICommands.ADD, _value, 0, null);
            transporter.send(serializer.serialize(packet));
        }
    }

    public void remove(final ValueDescription<?> _value) {

        if (valueCache.containsKey(_value.id)) {
            // remove
            valueCache.remove(_value.id);
        }
        else {
            System.out.println("value not in cache - ignore");
        }

        if (transporter != null) {
            final Packet packet = new Packet(ICommands.REMOVE, _value, 0, null);
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

            if (aPacket.command.equals(ICommands.UPDATE)) {

                _update(aPacket);
            }
            else if (aPacket.command.equals(ICommands.VERSION)) {

                // try to convert to version object
                System.out.println("version object yet to be specified");
            }
            else if (aPacket.command.equals(ICommands.INIT)) {

                _init(aPacket);
            }
            else {

                System.err.println("not implemented command: " + aPacket.command);
            }
        }
        else {

            System.err.println("no packet: " + new String(_data));
        }
    }

    private void _init(final Packet _packet) {

        // init with all values
        valueCache.forEach((_s, _valueDescription) -> {

            System.out.println("sending ::: " + _valueDescription.description);

            final Packet packet = new Packet(ICommands.ADD, _valueDescription, 0, null);
            transporter.send(serializer.serialize(packet));
        });

        if (initListener != null) {
            initListener.init();
        }
    }

    private void _update(final Packet _packet) {

        // try to convert data to ValueDescription
        try {

            final ValueDescription<?> val = serializer.convertToValueDescription(_packet.data);

            switch (_packet.command) {
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