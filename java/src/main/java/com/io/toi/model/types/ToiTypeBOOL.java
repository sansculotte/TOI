package com.io.toi.model.types;

import com.io.toi.model.ToiTypeDefinition;
import com.io.toi.model.ToiTypes.*;
import com.io.toi.model.exceptions.ToiDataErrorExcpetion;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;

public class ToiTypeBOOL extends ToiTypeDefinition<Boolean> {

    public static ToiTypeBOOL parse(final KaitaiStream _io) throws ToiDataErrorExcpetion {

        final ToiTypeBOOL type = new ToiTypeBOOL();

        // parse optionals
        while (true) {

            int          did    = _io.readU1();

            if (did == Packet.TERMINATOR.id()) {
                // terminator
                break;
            }

            final TypeDefinition dataid = TypeDefinition.byId(did);

            if (dataid == null) {
                throw new ToiDataErrorExcpetion();
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

        super(Datatype.BOOL);
    }

    @Override
    public ToiTypeDefinition<Boolean> cloneEmpty() {

        return new ToiTypeBOOL();
    }

    @Override
    public void write(final OutputStream _outputStream) throws IOException {

        super.write(_outputStream);

        // finalize typedefinition with terminator
        _outputStream.write((int)Packet.TERMINATOR.id());
    }

    @Override
    public void writeValue(final Boolean _value, final OutputStream _outputStream) throws IOException {
        _outputStream.write(_value ? 1 : 0);
    }

    @Override
    public Boolean getTypeDefault() {

        return false;
    }
}
