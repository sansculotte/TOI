package com.io.toui.model;

/**
 * Created by inx on 30/11/16.
 */
public interface ITransporter extends ITransporterListener {

    void send(final byte[] _data);

    void send(Packet<?> _packet);

    void setListener(final ITransporterListener _listener);

    void setSerializer(final Class<ITOUISerializer> _serializerClass);

    Class<ITOUISerializer> getSerializer();
}
