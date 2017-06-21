package com.io.toui.model.types;

import com.io.toui.model.*;
import com.io.toui.model.ToiTypes.LongString;
import com.io.toui.model.ToiTypes.TouiDatatypes;
import com.io.toui.model.ToiTypes.TouiTypedef;
import io.kaitai.struct.KaitaiStream;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeSTRING extends ToiTypeDefinition<String> {

    public static ToiTypeSTRING parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeSTRING type = new ToiTypeSTRING();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId((long)_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    LongString longString = new LongString(_io);
                    type.setDefaultValue(longString.data());
                    break;

                default:
                    throw new ToiDataErrorExcpetion();
            }

        }

        return type;
    }

    public ToiTypeSTRING() {

        super(TouiDatatypes.LSTR);
    }

    @Override
    public ToiTypeDefinition<String> cloneEmpty() {

        return new ToiTypeSTRING();
    }

    @Override
    public void writeValue(final String _value, final OutputStream _outputStream) throws
                                                                                  IOException {

        ToiParser.writeLongString(_value, _outputStream);
    }

}
