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
public class ToiTypeINT16 extends ToiTypeNumber<Short> {

    //----------------------------------------------------------------
    //
    public static ToiTypeINT16 parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeINT16 type = new ToiTypeINT16();

        // parse optionals
        while (true) {

            final TouiTypedef dataid = TouiTypedef.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    type.setDefaultValue(_io.readS2be());
                    break;
                case MIN:
                    type.setMin(_io.readS2be());
                    break;
                case MAX:
                    type.setMax(_io.readS2be());
                    break;
                case MULT:
                    type.setMultipleof(_io.readS2be());
                    break;

                default:
                    ToiTypeNumber.parseOption(type, dataid, _io);
            }

        }

        return type;
    }

    //----------------------------------------------------------------
    //
    public ToiTypeINT16() {
        super(TouiDatatypes.INT16);
    }

    public ToiTypeINT16(final short _default) {
        this();

        setDefaultValue(_default);
    }

    public ToiTypeINT16(final short _default, final short _min, final short _max) {
        this(_default);

        setMin(_min);
        setMax(_max);
    }

    public ToiTypeINT16(final short _default, final short _min, final short _max, short
            _multipleof) {

        this(_default, _min, _max);

        setMultipleof(_multipleof);
    }

    @Override
    public ToiTypeDefinition<Short> cloneEmpty() {

        return new ToiTypeINT16();
    }

    //----------------------------------------------------------------
    //
    @Override
    public void writeValue(final Short _value, final OutputStream _outputStream) throws
                                                                                 IOException {
        _outputStream.write(ByteBuffer.allocate(2).putShort(_value).array());
    }
}
