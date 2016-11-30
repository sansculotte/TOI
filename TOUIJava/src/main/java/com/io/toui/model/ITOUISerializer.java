package com.io.toui.model;

/**
 * Created by inx on 29/11/16.
 */
public interface ITOUISerializer {

    byte[] serialize(final Packet _packet);

    Packet deserialize(byte[] _data);

    ValueDescription<?> convertToValueDescription(Object _o);
}
