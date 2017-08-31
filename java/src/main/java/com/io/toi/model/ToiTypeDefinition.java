package com.io.toi.model;

import com.io.toi.model.ToiTypes.*;
import com.io.toi.model.exceptions.ToiDataErrorException;
import com.io.toi.model.types.*;
import io.kaitai.struct.KaitaiStream;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ToiTypeDefinition<T> implements ToiWritable {

//    public static <E> ToiTypeDefinition<E> create(final ToiTypeDefinition<E> _definition) {
//
//        switch (_definition.getTypeid()) {
//
//            case BOOL:
//                return new ToiTypeBOOL();
//            // number types
//            case INT8:
//                return new ToiTypeINT8();
//            case UINT8:
//                return new ToiTypeUINT8();
//            case INT16:
//                return new ToiTypeINT16();
//            case UINT16:
//                return new ToiTypeUINT16();
//            case INT32:
//                return new ToiTypeINT32();
//            case UINT32:
//                return new ToiTypeUINT32();
//            case INT64:
//                return new ToiTypeINT64();
//            case UINT64:
//                return new ToiTypeUINT64();
//            case FLOAT32:
//                return new ToiTypeFLOAT32();
//            case FLOAT64:
//                return new ToiTypeFLOAT64();
//
//            // string
//            case TSTR:
//                break;
//            case SSTR:
//                break;
//            case LSTR:
//                return new ToiTypeSTRING();
//        }
//
//        return new ToiTypeSTRING();
//    }

    //------------------------------------------------------------
    public static ToiTypeDefinition<?> parse(final KaitaiStream _io) throws ToiDataErrorException {

        // read mandatory type
        final Datatype typeid = Datatype.byId(_io.readU1());

        if (typeid == null) {
            throw new ToiDataErrorException();
        }

        ToiTypeDefinition<?> type = null;

        switch (typeid) {

            case BOOL:
                type = ToiTypeBOOL.parse(_io);
                break;

            // number types
            case INT8:
                type = ToiTypeINT8.parse(_io);
                break;
            case UINT8:
                type = ToiTypeUINT8.parse(_io);
                break;
            case INT16:
                type = ToiTypeINT16.parse(_io);
                break;
            case UINT16:
                type = ToiTypeUINT16.parse(_io);
                break;
            case INT32:
                type = ToiTypeINT32.parse(_io);
                break;
            case UINT32:
                type = ToiTypeUINT32.parse(_io);
                break;
            case INT64:
                type = ToiTypeINT64.parse(_io);
                break;
            case UINT64:
                type = ToiTypeUINT64.parse(_io);
                break;
            case FLOAT32:
                type = ToiTypeFLOAT32.parse(_io);
                break;
            case FLOAT64:
                type = ToiTypeFLOAT64.parse(_io);
                break;

            // string
            case TSTR:
                break;
            case SSTR:
                break;
            case LSTR:
                type = ToiTypeSTRING.parse(_io);
                break;
        }

        if (type == null) {
            throw new ToiDataErrorException();
        }

        return type;
    }

    //------------------------------------------------------------
    private final Datatype typeid;

    private T defaultValue;

    //------------------------------------------------------------
    public ToiTypeDefinition(final ToiTypes.Datatype _typeid) {

        typeid = _typeid;
    }

    public abstract ToiTypeDefinition<T> cloneEmpty();


    @Override
    public void write(final OutputStream _outputStream) throws IOException {

        _outputStream.write((int)typeid.id());

        if (defaultValue != null) {
            _outputStream.write((int)TypeDefinition.DEFAULTVALUE.id());
            writeValue(defaultValue, _outputStream);
        }
    }

    public abstract void writeValue(final T _value, final OutputStream _outputStream) throws
                                                                                      IOException;

//    public abstract void update(ToiTypeDefinition<?> _othertype);

    //------------------------------------------------------------
    public Datatype getTypeid() {

        return typeid;
    }

    public T getDefaultValue() {

        if (defaultValue == null) {
            return getTypeDefault();
        }

        return defaultValue;
    }

    public abstract T getTypeDefault();


    public void setDefaultValue(final T _defaultValue) {

        defaultValue = _defaultValue;
    }

}
