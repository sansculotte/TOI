package com.io.toui.model;

/**
 * Created by inx on 29/11/16.
 */
public class Packet {

    //------------------------------------------------------------
    //
    public String command;

    public Object data;

    public Long timestamp;

    public String id;

    //------------------------------------------------------------
    //
    public Packet() {
    }

    public Packet(
            final String _command, final Object _data, final Long _timestamp, final
    String _id) {

        command = _command;
        data = _data;
        timestamp = _timestamp;
        id = _id;
    }
}
