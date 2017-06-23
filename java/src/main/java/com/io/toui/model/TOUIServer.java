package com.io.toui.model;

import com.io.toui.model.ICommands.Init;
import com.io.toui.model.ToiTypes.Command;
import com.io.toui.model.ToiTypes.Command;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import com.io.toui.model.exceptions.ToiUnsupportedFeatureException;
import io.kaitai.struct.KaitaiStream;

import java.util.Objects;

/**
 * Created by inx on 30/11/16.
 */
public class TOUIServer extends TOUIBase {

    //------------------------------------------------------------
    // callback objects
    private Init initListener;

    //------------------------------------------------------------
    //
    public TOUIServer(final ITransporter _trans) {

        super(_trans);
    }

    //------------------------------------------------------------
    //
    public void setInitListener(final Init _listener) {

        initListener = _listener;
    }

    //------------------------------------------------------------
    //
    public void add(final ToiParameter<?> _value) {

        operateOnCache(valueCache -> {
            if (!valueCache.containsKey((int)_value.getId())) {
                // added
                valueCache.put((int)_value.getId(), _value);
            }
            else {

                if (!Objects.equals(valueCache.get((int)_value.getId()), _value)) {
                    System.err.println("different object with same ID!!!");
                } else {
                    System.out.println("already added value with this id - ignore");
                }
            }
        });

        if (transporter != null) {
            // TODO: send to all clients
            final ToiPacket packet = new ToiPacket(Command.ADD, _value);
            transporter.send(packet);
        }
    }

    public void remove(final ToiParameter<?> _value) {

        operateOnCache(valueCache -> {

            if (valueCache.containsKey((int)_value.getId())) {
                // removed
                valueCache.remove(_value.getId());
            }
            else {
                System.out.println("value not in cache - ignore");
            }
        });


        if (transporter != null) {
            final ToiPacket packet = new ToiPacket(Command.REMOVE, _value);
            transporter.send(packet);
        }
    }

    //------------------------------------------------------------
    //
    @Override
    public void received(final byte[] _data) {

        try {
            // deserialize
            final ToiPacket toiPacket = ToiPacket.parse(new KaitaiStream(_data));

            received(toiPacket);
        }
        catch (ToiDataErrorExcpetion | ToiUnsupportedFeatureException _e) {
            _e.printStackTrace();
        }
    }

    @Override
    public void received(final ToiPacket _packet) {

        if (_packet == null) {
            System.err.println("no packet... ");
            return;
        }

        if (_packet.getCmd() == Command.UPDATE) {

            _update(_packet);
        }
        else if (_packet.getCmd() == Command.VERSION) {

            // try to convert to version object
            System.out.println("version object yet to be specified");
        }
        else if (_packet.getCmd() == Command.INIT) {

            _init(_packet);
        }
        else {

            System.err.println("not implemented command: " + _packet.getCmd());
        }

    }

    private void _init(final ToiPacket _packet) {

        System.out.println("GOT INIT");

        operateOnCache(valueCache -> {

            // init with all values
            valueCache.forEach((_s, _parameter) -> {

                System.out.println("sending ::: " + _parameter.getDescription());

                final ToiPacket packet = new ToiPacket(Command.ADD, _parameter);
                transporter.send(packet);
            });

        });


        if (initListener != null) {
            initListener.init();
        }
    }

    private void _update(final ToiPacket _packet) {

        // try to convert data to TypeDefinition
        try {

            final ToiParameter<?> val = (ToiParameter<?>)_packet.getData();

            switch (_packet.getCmd()) {
                case UPDATE:
                    if (updateListener != null) {
                        updateListener.updated(val);
                    }

                    // TODO: should call own updated here?
                    // -> this should updated all clients...
                    // optimize, do not send back to same client??
                    // maybe let listener decide to updated all clients??

                    break;

                default:
                    System.err.println("no such command implemented in server: " + _packet.getCmd());
            }

        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
