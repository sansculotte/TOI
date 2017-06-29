package com.io.toi.model;

/**
 * Created by inx on 30/11/16.
 */
public interface ITransporter extends ITransporterListener {

    //void send(final byte[] _data);

    void send(ToiPacket _packet);

    void setListener(final ITransporterListener _listener);
}
