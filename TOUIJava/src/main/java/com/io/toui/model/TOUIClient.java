package com.io.toui.model;

import com.io.toui.model.ICommands.Add;
import com.io.toui.model.ICommands.Remove;
import com.io.toui.model.ToiTypes.TouiCommands;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import com.io.toui.model.exceptions.ToiUnsupportedFeatureException;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;

/**
 * Created by inx on 30/11/16.
 */
public class TOUIClient extends TOUIBase {

    // callback objects
    private Add addListener;

    private Remove removeListener;

    //------------------------------------------------------------
    //
    public TOUIClient(final ITransporter _trans) {

        super(_trans);
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

        operateOnCache(valueCache -> {
            // clear cache?
            valueCache.clear();
        });

        if (transporter != null) {
            // send to all clients
            final ToiPacket packet = new ToiPacket(TouiCommands.INIT);
            transporter.send(packet);
        }
    }

    //------------------------------------------------------------
    //
    @Override
    public void received(final byte[] _data) {

        // deserialize
        try {
            final ToiPacket toiPacket = ToiPacket.parse(new KaitaiStream(_data));

            received(toiPacket);
        }
        catch (ToiUnsupportedFeatureException | ToiDataErrorExcpetion _e) {
            _e.printStackTrace();
        }
    }

    @Override
    public void received(final ToiPacket _packet) {

        if (_packet == null) {
            System.err.println("no packet...");
            return;
        }

        if ((_packet.getCmd() == TouiCommands.ADD) ||
            (_packet.getCmd() == TouiCommands.UPDATE) ||
            (_packet.getCmd() == TouiCommands.REMOVE)) {

            _update(_packet);
        }
        else if (_packet.getCmd() == TouiCommands.VERSION) {

            // try to convert to version object
            System.out.println("version object yet to be specified");
        }
        else {

            System.err.println("not implemented command: " + _packet.getCmd());
        }

    }

    private void _update(final ToiPacket _packet) {

        // try to convert data to TypeDefinition
        try {

            final ToiParameter<?> val = (ToiParameter<?>)_packet.getData();

            switch (_packet.getCmd()) {
                case ADD:

                    operateOnCache(valueCache -> {

                        // added to value cache?
                        if (!valueCache.containsKey((int)val.getId())) {

                            valueCache.put((int)val.getId(), val);

                            // inform listener
                            if (addListener != null) {
                                addListener.added(val);
                            }
                        }
                        else {
                            System.err.println("client: added: already has value with id: " +
                                               val.getId());
                        }
                    });

                    break;

                case REMOVE:

                    operateOnCache(valueCache -> {

                        if (valueCache.containsKey((int)val.getId())) {
                            final ToiParameter<?> removed = valueCache.remove(val.getId());

                            // inform listener
                            if (removeListener != null) {
                                removeListener.removed(removed);
                            }
                        }
                        else {
                            System.err.println("client: removed: does not know value with id: " +
                                               val.getId());
                        }
                    });

                    break;

                case UPDATE:

                    operateOnCache(valueCache -> {
                        //updated value cache?
                        final ToiParameter<?> cached = valueCache.get((int)val.getId());
                        if (cached != null) {
                            cached.update(val);

                            // inform listener
                            if (updateListener != null) {
                                updateListener.updated(cached);
                            }

                        }
                        else {
                            System.err.println("client: updated: no value in value cache - " +
                                               "ignoring");
                        }

                    });

                    break;

                default:
                    System.err.println("no such command implemented in client: " +
                                       _packet.getCmd());
            }

        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
