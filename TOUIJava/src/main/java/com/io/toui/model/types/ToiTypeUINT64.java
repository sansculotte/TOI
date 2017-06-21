package com.io.toui.model.types;

import com.io.toui.model.ToiTypeDefinition;
import com.io.toui.model.ToiTypes.TouiDatatypes;
import com.io.toui.model.ToiTypes.TouiTypedef;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeUINT64 extends ToiTypeNumber<Long> {

    public static ToiTypeUINT64 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeUINT64 type = new ToiTypeUINT64();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
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

        super(TouiDatatypes.UINT64);
    }

    @Override
    public ToiTypeDefinition<Long> cloneEmpty() {

        return new ToiTypeUINT64();
    }

    @Override
    public void writeValue(final Long _value, final OutputStream _outputStream) throws IOException {

        _outputStream.write(ByteBuffer.allocate(8).putLong(_value).array());
    }
}
