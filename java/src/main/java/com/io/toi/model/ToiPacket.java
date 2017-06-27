package com.io.toi.model;

import com.io.toi.model.ToiTypes.Command;
import com.io.toi.model.ToiTypes.Packet;
import com.io.toi.model.exceptions.ToiDataErrorExcpetion;
import com.io.toi.model.exceptions.ToiUnsupportedFeatureException;
import io.kaitai.struct.KaitaiStream;

import java.io.*;
import java.nio.ByteBuffer;

/**
 * Created by inx on 13/06/17.
 */
public class ToiPacket implements ToiWritable {

    public static final byte[] TOI_MAGIC = { 4, 15, 5, 9 };

    public static byte[] serialize(final ToiPacket _packet) throws IOException {

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

            _packet.write(os);
            return os.toByteArray();
        }
    }

    public static ToiPacket parse(final KaitaiStream _io) throws
                                                          ToiUnsupportedFeatureException,
                                                          ToiDataErrorExcpetion {

        final Command cmd = Command.byId(_io.readU1());

        if (cmd == null) {
            throw new ToiDataErrorExcpetion();
        }

        final ToiPacket packet = new ToiPacket(cmd);

        // read packet options
        while (!_io.isEof()) {

            final int did = _io.readU1();

            if (did == Packet.TERMINATOR.id()) {
                // terminator
                break;
            }

            final Packet dataid = Packet.byId(did);

            if (dataid == null) {
                // wrong data id... skip whole packet?
                throw new ToiDataErrorExcpetion();
            }

            switch (dataid) {
                case DATA:

                    if (packet.getData() != null) {
                        throw new ToiDataErrorExcpetion();
                    }

                    switch (cmd) {
                        case INIT:
                            // init - shout not happen
                            throw new ToiDataErrorExcpetion();

                        case ADD:
                        case REMOVE:
                        case UPDATE:
                            // expect parameter
                            packet.setData(ToiParameter.parse(_io));

                            break;
                        case VERSION:
                            // version: expect meta
                            // TODO: implement
                            break;
                    }

                    break;
                case ID:
                    packet.setPacketId(_io.readU4be());
                    break;
                case TIMESTAMP:
                    packet.setTimestamp(_io.readU8be());
                    break;
                default:
                    throw new ToiDataErrorExcpetion();
            }

        }

        return packet;
    }

    //--------------------------------------------------------
    private final Command cmd;

    private Long packetId;

    private Long timestamp;

    private ToiWritable data;

    //--------------------------------------------------------
    //--------------------------------------------------------
    public ToiPacket(final Command _cmd) {

        this(_cmd, null);
    }

    public ToiPacket(final Command _cmd, final ToiWritable _data) {

        cmd = _cmd;
        data = _data;
    }

    //--------------------------------------------------------
    public void write(final boolean _magic, final OutputStream _outputStream) throws IOException {

        if (_magic) {
            // write magic
            _outputStream.write(TOI_MAGIC);
        }

        write(_outputStream);
    }


    @Override
    public void write(final OutputStream _outputStream) throws IOException {

        // write mandatory command
        _outputStream.write((int)cmd.id());

        if (packetId != null) {
            _outputStream.write((int)Packet.ID.id());
            _outputStream.write(ByteBuffer.allocate(4).putInt(packetId.intValue()).array());
        }

        if (timestamp != null) {
            _outputStream.write((int)Packet.TIMESTAMP.id());
            _outputStream.write(ByteBuffer.allocate(8).putLong(timestamp).array());
        }

        if (data != null) {
            _outputStream.write((int)Packet.DATA.id());
            data.write(_outputStream);
        }

        // finalize packet with terminator
        _outputStream.write((int)Packet.TERMINATOR.id());
    }

    //--------------------------------------------------------

    public Command getCmd() {

        return cmd;
    }

    public Long getPacketId() {

        return packetId;
    }

    public void setPacketId(final long _packetId) {

        packetId = _packetId;
    }

    public Long getTimestamp() {

        return timestamp;
    }

    public void setTimestamp(final long _timestamp) {

        timestamp = _timestamp;
    }

    public ToiWritable getData() {

        return data;
    }

    public void setData(final ToiWritable _data) {

        data = _data;
    }
}
