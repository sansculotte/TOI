package com.io.toi.model.types;

import com.io.toi.model.ToiTypeDefinition;
import com.io.toi.model.ToiTypes.*;
import com.io.toi.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeFLOAT32 extends ToiTypeNumber<Float> {

    public static ToiTypeFLOAT32 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeFLOAT32 type = new ToiTypeFLOAT32();

        // parse optionals
        while (true) {

            int          did    = _io.readU1();

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
                    type.setDefaultValue(_io.readF4be());
                    break;
                case MIN:
                    type.setMin(_io.readF4be());
                    break;
                case MAX:
                    type.setMax(_io.readF4be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readF4be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeFLOAT32() {
        super(Datatype.FLOAT32);
    }

    @Override
    public ToiTypeDefinition<Float> cloneEmpty() {

        return new ToiTypeFLOAT32();
    }

    @Override
    public void writeValue(final Float _value, final OutputStream _outputStream) throws IOException {
        _outputStream.write(ByteBuffer.allocate(4).putFloat(_value).array());
    }

    @Override
    public Float getTypeDefault() {

        return 0.f;
    }
}
