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
            final Packet packet = new Packet(ICommands.INIT, null, 0L, null);
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

            // sanity check
            if ((val.id == null) || val.id.isEmpty()) {
                System.err.println("ignoring value description without id");
                return;
            }

            switch (_packet.command) {
                case ICommands.ADD:

                    // added to value cache?
                    if (!valueCache.containsKey(val.id)) {

                        valueCache.put(val.id, val);

                        // inform listener
                        if (addListener != null) {
                            addListener.added(val);
                        }
                    }
                    else {
                        System.err.println("client: added: already has value with id: " + val.id);
                    }


                    break;

                case ICommands.REMOVE:

                    if (valueCache.containsKey(val.id)) {
                        valueCache.remove(val.id);

                        // inform listener
                        if (removeListener != null) {
                            removeListener.removed(val);
                        }
                    }
                    else {
                        System.err.println("client: removed: does not know value with id: " + val.id);
                    }

                    break;

                case ICommands.UPDATE:

                    //updated value cache?
                    final ValueDescription<?> cached = valueCache.get(val.id);
                    if (cached != null) {
                        cached.update(val);

                        // inform listener
                        if (updateListener != null) {
                            updateListener.updated(val);
                        }

                    } else {
                        System.err.println("client: updated: no value in value cache - ignoring");
                    }

                    break;

                default:
                    System.err.println("no such command implemented in client: " + _packet.command);
            }

        }
        catch (final IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
}
