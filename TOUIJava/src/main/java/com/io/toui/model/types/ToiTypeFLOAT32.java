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
public class ToiTypeFLOAT32 extends ToiTypeNumber<Float> {

    public static ToiTypeFLOAT32 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeFLOAT32 type = new ToiTypeFLOAT32();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
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
        super(TouiDatatypes.FLOAT32);
    }

    @Override
    public ToiTypeDefinition<Float> cloneEmpty() {

        return new ToiTypeFLOAT32();
    }

    @Override
    public void writeValue(final Float _value, final OutputStream _outputStream) throws IOException {
        _outputStream.write(ByteBuffer.allocate(4).putFloat(_value).array());
    }
}
