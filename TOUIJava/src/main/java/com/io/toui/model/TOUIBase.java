package com.io.toui.model;

import com.io.toui.model.ICommands.Update;

import java.util.*;

/**
 * Created by inx on 30/11/16.
 */
public abstract class TOUIBase implements ITransporterListener {

    //------------------------------------------------------------
    //
    protected ITOUISerializer serializer;

    protected ITransporter transporter;

    protected final Map<String, ValueDescription<?>> valueCache = new HashMap<>();

    // callback objects
    protected Update updateListener;

    //------------------------------------------------------------
    //
    public TOUIBase(final ITOUISerializer _ser, final ITransporter _trans) {

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

    public Map<String, ValueDescription<?>> getValueCache() {
        return Collections.unmodifiableMap(valueCache);
    }

    //------------------------------------------------------------
    //
    public void setUpdateListener(final Update _listener) {

        updateListener = _listener;
    }


    //------------------------------------------------------------
    //
    /**
     * send value updated using the transporter
     *
     * @param _value
     *      the value to updated
     */
    public void update(final ValueDescription<?> _value) {

        // updated valuecache
        if (valueCache.containsKey(_value.id)) {
            // get cached value
            final ValueDescription<?> desc = valueCache.get(_value.id);

            desc.update(_value);
        }
        else {
            System.out.println("value not in cache - ignore");
        }

        if (transporter != null) {

            // transport value
            final Packet packet = new Packet(ICommands.UPDATE, _value, 0L, null);
            transporter.send(serializer.serialize(packet));
        }
    }

    public void dumpCache() {
        valueCache.forEach((_s, _valueDescription) -> {
            System.out.println("------");
            _valueDescription.dump();
            System.out.println();
        });
    }
}
