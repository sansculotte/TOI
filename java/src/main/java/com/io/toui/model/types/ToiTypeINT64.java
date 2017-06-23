package com.io.toui.model.types;

import com.io.toui.model.ToiTypeDefinition;
import com.io.toui.model.ToiTypes.*;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeINT64 extends ToiTypeNumber<Long> {

    public static ToiTypeINT64 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeINT64 type = new ToiTypeINT64();

        // parse optionals
        while (true) {

            int did = _io.readU1();

            if (did == Packet.TERMINATOR.id()) {
                // terminator
                break;
            }

            final TypeNumber dataid = TypeNumber.byId(did);

            if (dataid == null) {
                throw new ToiDataErrorExcpetion();
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue(_io.readS8be());
                    break;
                case MIN:
                    type.setMin(_io.readS8be());
                    break;
                case MAX:
                    type.setMax(_io.readS8be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readS8be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeINT64() {

        super(Datatype.INT64);
    }

    @Override
    public ToiTypeDefinition<Long> cloneEmpty() {

        return new ToiTypeINT64();
    }

    @Override
    public void writeValue(final Long _value, final OutputStream _outputStream) throws IOException {

        _outputStream.write(ByteBuffer.allocate(8).putLong(_value).array());
    }

    @Override
    public Long getTypeDefault() {

        return Long.valueOf(0);
    }
}
