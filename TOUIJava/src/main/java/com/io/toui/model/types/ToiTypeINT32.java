package com.io.toui.model.types;

import com.io.toui.model.ToiTypeDefinition;
import com.io.toui.model.ToiTypes;
import io.kaitai.struct.KaitaiStream;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;

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

            final ToiTypes.TouiTypedef dataid = ToiTypes.TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
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
        super(ToiTypes.TouiDatatypes.INT32);
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
}
