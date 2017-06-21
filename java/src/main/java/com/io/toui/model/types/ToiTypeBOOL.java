package com.io.toui.model.types;

import com.io.toui.model.ToiTypeDefinition;
import com.io.toui.model.ToiTypes.TouiDatatypes;
import com.io.toui.model.ToiTypes.TouiTypedef;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;

public class ToiTypeBOOL extends ToiTypeDefinition<Boolean> {

    public static ToiTypeBOOL parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeBOOL type = new ToiTypeBOOL();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue((_io.readU1() > 0));
                    break;

                default:
                    throw new ToiDataErrorExcpetion();
            }

        }

        return type;
    }

    public ToiTypeBOOL() {

        super(TouiDatatypes.BOOL);
    }

    @Override
    public ToiTypeDefinition<Boolean> cloneEmpty() {

        return new ToiTypeBOOL();
    }

    @Override
    public void writeValue(final Boolean _value, final OutputStream _outputStream) throws IOException {
        _outputStream.write(_value ? 1 : 0);
    }
}
