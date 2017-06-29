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
public class ToiTypeINT32 extends ToiTypeNumber<Integer> {

    public static ToiTypeINT32 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeINT32 type = new ToiTypeINT32();

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
                    type.setDefaultValue(_io.readS4be());
                    break;
                case MIN:
                    type.setMin(_io.readS4be());
                    break;
                case MAX:
                    type.setMax(_io.readS4be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readS4be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeINT32() {
        super(Datatype.INT32);
    }

    @Override
    public ToiTypeDefinition<Integer> cloneEmpty() {

        return new ToiTypeINT32();
    }

    @Override
    public void writeValue(final Integer _value, final OutputStream _outputStream) throws
                                                                                   IOException {
        _outputStream.write(ByteBuffer.allocate(4).putInt(_value).array());
    }

    @Override
    public Integer getTypeDefault() {

        return 0;
    }
}
