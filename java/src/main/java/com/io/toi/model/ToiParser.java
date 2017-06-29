package com.io.toi.model;

import com.io.toi.model.ToiTypes.Command;
import com.io.toi.model.exceptions.ToiDataErrorExcpetion;
import com.io.toi.model.exceptions.ToiUnsupportedFeatureException;
import com.io.toi.model.types.ToiTypeINT16;
import io.kaitai.struct.KaitaiStream;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Created by inx on 13/06/17.
 */
public class ToiParser {

    public static void writeTinyString(
            final String _string, final OutputStream _outputStream) throws IOException {

        final byte[] bytes = _string.getBytes(Charset.forName("UTF-8"));

        if (bytes.length > 255) {
            // TODO log error
            System.err.println("unit string is too long");
        }

        // write length
        _outputStream.write(bytes.length);
        _outputStream.write(bytes);
    }

    public static void writeShortString(
            final String _string, final OutputStream _outputStream) throws IOException {

        final byte[] bytes = _string.getBytes(Charset.forName("UTF-8"));

        if (bytes.length > 65535) {
            // TODO log error
            System.err.println("unit string is too long for short string");
        }

        // write length
        _outputStream.write(ByteBuffer.allocate(2).putShort((short)bytes.length).array());
        _outputStream.write(bytes);
    }

    public static void writeLongString(
            final String _string, final OutputStream _outputStream) throws IOException {

        final byte[] bytes = _string.getBytes(Charset.forName("UTF-8"));

        // write length
        _outputStream.write(ByteBuffer.allocate(4).putInt(bytes.length).array());
        _outputStream.write(bytes);
    }

    //----------------------------------------------------------------
    public static ToiPacket fromFile(final String fileName) throws
                                                            IOException,
                                                            ToiUnsupportedFeatureException,
                                                            ToiDataErrorExcpetion {

        final KaitaiStream _io = new KaitaiStream(fileName);

        // we read from a file: no magic expected
        //        _io.ensureFixedContents(ToiPacket.TOI_MAGIC);

        // got magic parse packet
        final ToiPacket packet = ToiPacket.parse(_io);

        return packet;
    }

    public static void main(final String[] args) {

        try {

            final ToiPacket packet = fromFile(
                    "/Users/inx/Documents/_github/TOI/example-data/packet_bool_no_user.toi");
            //            final ToiPacket packet = fromFile
            // ("/Users/inx/Documents/_github/TOI/example-data/packet_lstr_no_user.toi");
            //            final ToiPacket packet = fromFile
            // ("/Users/inx/Documents/_github/TOI/example-data/packet_s8_no_user.toi");
            //            final ToiPacket packet = fromFile
            // ("/Users/inx/Documents/_github/TOI/example-data/packet_u32_no_user.toi");
            //            final ToiPacket packet = fromFile
            // ("/Users/inx/Documents/_github/TOI/example-data/packet_init.toi");

            System.out.println(packet.getCmd());

            // create a packet
            final ToiPacket newP = new ToiPacket(Command.UPDATE);
            newP.setTimestamp(1234);

            final ToiParameter<Short> param = new ToiParameter<>(12,
                                                                 new ToiTypeINT16((short)33,
                                                                                  (short)10,
                                                                                  (short)100));

            param.setLabel("a short value");
            param.setDescription("longer description");
            param.setOrder(-1);
            param.setValue((short)55);

            newP.setData(param);

            try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {

                newP.write(os);

                final byte[] the_bytes = os.toByteArray();

                try (OutputStream fs = new FileOutputStream
                        ("/Users/inx/Documents/_toui/_generated" +
                                                            ".toi")) {

                    os.writeTo(fs);
                }
            }

        }
        catch (IOException | ToiDataErrorExcpetion | ToiUnsupportedFeatureException _e) {
            _e.printStackTrace();
        }

    }

}
