package com.io.toui.model.types;

import com.io.toui.model.ToiTypeDefinition;
import com.io.toui.model.ToiTypes.TouiDatatypes;
import com.io.toui.model.ToiTypes.TouiTypedef;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeINT8 extends ToiTypeNumber<Byte> {

    public static ToiTypeINT8 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeINT8 type = new ToiTypeINT8();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue(_io.readS1());
                    break;
                case MIN:
                    type.setMin(_io.readS1());
                    break;
                case MAX:
                    type.setMax(_io.readS1());
                    break;
                case MULT:
                    type.setMultipleof(_io.readS1());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeINT8() {

        super(TouiDatatypes.INT8);
    }

    @Override
    public ToiTypeDefinition<Byte> cloneEmpty() {

        return new ToiTypeINT8();
    }

    @Override
    public void writeValue(final Byte _value, final OutputStream _outputStream) throws IOException {

        _outputStream.write(_value);
    }
}
