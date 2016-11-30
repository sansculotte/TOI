package com.io.toui.model;

import com.io.toui.model.ICommands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by inx on 30/11/16.
 */
public class TOUI implements ITransporterListener {

    //------------------------------------------------------------
    //
    private ITOUISerializer serializer;

    private ITransporter transporter;

    private final Map<String, ValueDescription<?>> valueCache = new HashMap<>();

    // callback objects
    private Update updateListener;

    private Add addListener;

    private Remove removeListener;

    private Init initListener;

    //------------------------------------------------------------
    //
    public TOUI(final ITOUISerializer _ser, final ITransporter _trans) {

        serializer = _ser;
        _setTransporter(_trans);
    }

    //------------------------------------------------------------
    //
    public void setSerializer(final ITOUISerializer _serializer) {

        serializer = _serializer;
    }

    public void setTransporter(final ITransporter _transporter) {

        _setTransporter(_transporter);
    }

    private void _setTransporter(final ITransporter _transporter) {

        transporter = _transporter;

        if (transporter != null) {
            transporter.setListener(this);
        }
    }

    //------------------------------------------------------------
    //
    public void setUpdateListener(final Update _listener) {

        updateListener = _listener;
    }

    public void setAddListener(final Add _listener) {

        addListener = _listener;
    }

    public void setRemoveListener(final Remove _listener) {

        removeListener = _listener;
    }

    public void setInitListener(final Init _listener) {

        initListener = _listener;
    }

    //------------------------------------------------------------
    //
    public void init() {

        // clear cache?
        valueCache.clear();

        if (transporter != null) {
            // send to all clients
            final Packet packet = new Packet(ICommands.INIT, null, 0, null);
            transporter.send(serializer.serialize(packet));
        }
    }

    public void update(final ValueDescription<?> _value) {

        // update valuecache
        if (valueCache.containsKey(_value.id)) {
            // get cached value
            final ValueDescription<?> desc = valueCache.get(_value.id);

            // update cached value

        }
        else {
            System.out.println("value not in cache - ignore");
        }

        if (transporter != null) {
            // transport
            final Packet packet = new Packet(ICommands.UPDATE, _value, 0, null);
            transporter.send(serializer.serialize(packet));
        }
    }

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

            if (aPacket.command.equals(ICommands.ADD) ||
                aPacket.command.equals(ICommands.UPDATE) ||
                aPacket.command.equals(ICommands.REMOVE)) {
                // try to convert data to ValueDescription
                try {

                    final ValueDescription<?>
                            val
                            = serializer.convertToValueDescription(aPacket.data);

                    switch (aPacket.command) {
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
                            System.err.println("no such command: " + aPacket.command);
                    }

                }
                catch (final IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            else if (aPacket.command.equals(ICommands.VERSION)) {
                // try to convert to version object
                System.out.println("version object yet to be specified");
            }
            else if (aPacket.command.equals(ICommands.INIT)) {

                // init with all values
                valueCache.forEach((_s, _valueDescription) -> {
                    final Packet packet = new Packet(ICommands.ADD, _valueDescription, 0, null);
                    transporter.send(serializer.serialize(packet));
                });

                if (initListener != null) {
                    initListener.init();
                }
            } else {
                System.err.println("not implemented command: " + aPacket.command);
            }

        }
        else {
            System.err.println("no packet: " + new String(_data));
        }
    }
}
