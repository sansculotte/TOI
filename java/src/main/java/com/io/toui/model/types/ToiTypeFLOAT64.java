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
public class ToiTypeFLOAT64 extends ToiTypeNumber<Double> {

    public static ToiTypeFLOAT64 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeFLOAT64 type = new ToiTypeFLOAT64();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue(_io.readF8be());
                    break;
                case MIN:
                    type.setMin(_io.readF8be());
                    break;
                case MAX:
                    type.setMax(_io.readF8be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readF8be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    public ToiTypeFLOAT64() {
        super(TouiDatatypes.FLOAT64);
    }

    public ToiTypeFLOAT64(
            final Double _min,
            final Double _max) {

        super(TouiDatatypes.FLOAT64, _min, _max, null);
    }

    public ToiTypeFLOAT64(
            final Double _min,
            final Double _max,
            final Double _multipleof) {

        super(TouiDatatypes.FLOAT64, _min, _max, _multipleof);
    }

    @Override
    public ToiTypeDefinition<Double> cloneEmpty() {

        return new ToiTypeFLOAT64();
    }

    @Override
    public void writeValue(final Double _value, final OutputStream _outputStream) throws
                                                                                  IOException {
        _outputStream.write(ByteBuffer.allocate(8).putDouble(_value).array());
    }
}
