package com.io.toi.model.types;

import com.io.toi.model.ToiTypeDefinition;
import com.io.toi.model.ToiTypes.*;
import com.io.toi.model.exceptions.ToiDataErrorException;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeUINT64 extends ToiTypeNumber<Long> {

    public static ToiTypeUINT64 parse(final KaitaiStream _io) throws ToiDataErrorException {

        final ToiTypeUINT64 type = new ToiTypeUINT64();

        // parse optionals
        while (true) {

            int did = _io.readU1();

            if (did == Packet.TERMINATOR.id()) {
                // terminator
                break;
            }

            final TypeNumber dataid = TypeNumber.byId(did);

            if (dataid == null) {
                throw new ToiDataErrorException();
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue(_io.readU8be());
                    break;
                case MIN:
                    type.setMin(_io.readU8be());
                    break;
                case MAX:
                    type.setMax(_io.readU8be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readU8be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeUINT64() {

        super(Datatype.UINT64);
    }

    @Override
    public ToiTypeDefinition<Long> cloneEmpty() {

        return new ToiTypeUINT64();
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
