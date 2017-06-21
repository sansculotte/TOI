package com.io.toui.model;

import com.io.toui.model.ToiTypes.TouiCommands;
import com.io.toui.model.ToiTypes.TouiPacket;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import com.io.toui.model.exceptions.ToiUnsupportedFeatureException;
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

        final TouiCommands cmd = TouiCommands.byId(_io.readU1());

        if (cmd == null) {
            throw new ToiDataErrorExcpetion();
        }

        final ToiPacket packet = new ToiPacket(cmd);

        // read packet options
        while (!_io.isEof()) {

            int             did    = _io.readU1();
            final TouiPacket dataid = TouiPacket.byId(did);

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
                case PACKET_ID:
                    packet.setPacketId(_io.readU4be());
                    break;
                case PACKET_TIME:
                    packet.setTimestamp(_io.readU8be());
                    break;
                default:
                    throw new ToiDataErrorExcpetion();
            }

        }

        return packet;
    }

    //--------------------------------------------------------
    private final TouiCommands cmd;

    private Long packetId;

    private Long timestamp;

    private ToiWritable data;

    //--------------------------------------------------------
    //--------------------------------------------------------
    public ToiPacket(final TouiCommands _cmd) {

        this(_cmd, null);
    }

    public ToiPacket(final TouiCommands _cmd, final ToiWritable _data) {

        cmd = _cmd;
        data = _data;
    }

    //--------------------------------------------------------
    @Override
    public void write(OutputStream _outputStream) throws IOException {

        // write magic
        _outputStream.write(TOI_MAGIC);

        // write mandatory command
        _outputStream.write((int)cmd.id());

        if (packetId != null) {
            _outputStream.write((int)TouiPacket.PACKET_ID.id());
            _outputStream.write(ByteBuffer.allocate(4).putInt(packetId.intValue()).array());
        }

        if (timestamp != null) {
            _outputStream.write((int)TouiPacket.PACKET_TIME.id());
            _outputStream.write(ByteBuffer.allocate(8).putLong(timestamp).array());
        }

        if (data != null) {
            _outputStream.write((int)TouiPacket.DATA.id());
            data.write(_outputStream);
        }

        // write terminator
        _outputStream.write(0);
    }

    //--------------------------------------------------------

    public TouiCommands getCmd() {

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
