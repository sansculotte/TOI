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
public class ToiTypeUINT8 extends ToiTypeNumber<Short> {

    public static ToiTypeUINT8 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeUINT8 type = new ToiTypeUINT8();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue((short)_io.readU1());
                    break;
                case MIN:
                    type.setMin((short)_io.readU1());
                    break;
                case MAX:
                    type.setMax((short)_io.readU1());
                    break;
                case MULT:
                    type.setMultipleof((short)_io.readU1());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeUINT8() {
        super(TouiDatatypes.UINT8);
    }

    @Override
    public ToiTypeDefinition<Short> cloneEmpty() {

        return new ToiTypeUINT8();
    }

    @Override
    public void writeValue(final Short _value, final OutputStream _outputStream) throws
                                                                                 IOException {
        _outputStream.write(_value);
    }
}
