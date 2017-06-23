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
public class ToiTypeUINT16 extends ToiTypeNumber<Integer> {

    public static ToiTypeUINT16 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeUINT16 type = new ToiTypeUINT16();

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
                    type.setDefaultValue(_io.readU2be());
                    break;
                case MIN:
                    type.setMin(_io.readU2be());
                    break;
                case MAX:
                    type.setMax(_io.readU2be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readU2be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeUINT16() {

        super(Datatype.UINT16);
    }

    @Override
    public ToiTypeDefinition<Integer> cloneEmpty() {

        return new ToiTypeUINT16();
    }

    @Override
    public void writeValue(final Integer _value, final OutputStream _outputStream) throws
                                                                                   IOException {

        _outputStream.write(ByteBuffer.allocate(2).putShort(_value.shortValue()).array());
    }

    @Override
    public Integer getTypeDefault() {

        return 0;
    }
}
