package com.io.toi.model;

public interface ITransporterListener {

    void received(final byte[] _data);

    void received(final ToiPacket _packet);
}
