package com.io.toi.model.types;

import com.io.toi.model.*;
import com.io.toi.model.ToiTypes.LongString;
import com.io.toi.model.ToiTypes.Datatype;
import com.io.toi.model.ToiTypes.Packet;
import com.io.toi.model.ToiTypes.TypeDefinition;
import io.kaitai.struct.KaitaiStream;
import com.io.toi.model.exceptions.ToiDataErrorException;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by inx on 13/06/17.
 */
public class ToiTypeSTRING extends ToiTypeDefinition<String> {

    public static ToiTypeSTRING parse(final KaitaiStream _io) throws ToiDataErrorException {

        final ToiTypeSTRING type = new ToiTypeSTRING();

        // parse optionals
        while (true) {

            final TypeDefinition dataid = TypeDefinition.byId(_io.readU1());

            if (dataid == null) {
                break;
            }

            switch (dataid) {

                case DEFAULTVALUE:
                    LongString longString = new LongString(_io);
                    type.setDefaultValue(longString.data());
                    break;

                default:
                    throw new ToiDataErrorException();
            }

        }

        return type;
    }

    public ToiTypeSTRING() {

        super(Datatype.LSTR);
    }

    @Override
    public ToiTypeDefinition<String> cloneEmpty() {

        return new ToiTypeSTRING();
    }

    @Override
    public void write(final OutputStream _outputStream) throws IOException {

        super.write(_outputStream);

        // finalize typedefinition with terminator
        _outputStream.write((int)Packet.TERMINATOR.id());
    }

    @Override
    public void writeValue(final String _value, final OutputStream _outputStream) throws
                                                                                  IOException {
        ToiParser.writeLongString(_value, _outputStream);
    }

    @Override
    public String getTypeDefault() {

        return "";
    }
}
