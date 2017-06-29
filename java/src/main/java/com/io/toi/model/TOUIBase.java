package com.io.toi.model;

import com.io.toi.model.ICommands.Update;
import com.io.toi.model.ToiTypes.Command;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by inx on 30/11/16.
 */
public abstract class TOUIBase implements ITransporterListener {

    //------------------------------------------------------------
    //
    protected ITransporter transporter;

    private final Map<Integer, ToiParameter<?>> valueCache = new ConcurrentHashMap<>();

    // callback objects
    protected Update updateListener;

    private final ReentrantLock lock = new ReentrantLock();

    //------------------------------------------------------------
    //
    public TOUIBase(final ITransporter _trans) {

        _setTransporter(_trans);
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

    public Map<Integer, ToiParameter<?>> getValueCache() {
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
    public void update(final ToiParameter<?> _value) {

        // updated valuecache
        if (valueCache.containsKey((int)_value.getId())) {
            // get cached value
            final ToiParameter<?> parameter = valueCache.get((int)_value.getId());

            parameter.update(_value);
        }
        else {
            System.out.println("value not in cache - ignore");
        }

        if (transporter != null) {

            // transport value
            final ToiPacket packet = new ToiPacket(Command.UPDATE, _value);
            transporter.send(packet);
        }
    }

    public void dumpCache() {
        valueCache.forEach((_s, _valueDescription) -> {
            System.out.println("------");
            _valueDescription.dump();
            System.out.println();
        });
    }

    public void operateOnCache(final ICacheOperator _operator) {

        // single point of entry
        // locking??

        // FIXME: right??
        _operator.operate(valueCache);
//        lock.lock();
//        try {
//        } finally {
//            lock.unlock();
//        }
    }
}
