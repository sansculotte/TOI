package com.io.toui.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.io.toui.model.Parameter;

/**
 * Created by inx on 29/11/16.
 */
public abstract class TypeDefinition<T> {

    public enum inttypes{
        INT_8,
        UINT_8,
        INT_16,
        UINT_16,
        INT_32,
        UINT_32
    }

    public enum longtypes{
        INT_64,
        UINT_64
    }

    public enum numtypes{
        INT_8,
        UINT_8,
        INT_16,
        UINT_16,
        INT_32,
        UINT_32,
        INT_64,
        UINT_64,
        FLOAT_32,
        FLOAT_64
    }

    //----------------------------------------------------
    // static
    public static final String BOOLEAN = "boolean";

    //public static final String NUMBER = "number";

    public static final String INT_8 = "int8";
    public static final String UINT_8 = "uint8";
    public static final String INT_16 = "int16";
    public static final String UINT_16 = "uint16";
    public static final String INT_32= "int32";
    public static final String UINT_32 = "uint32";
    public static final String INT_64 = "int64";
    public static final String UINT_64 = "uint64";
    public static final String FLOAT_32 = "float32";
    public static final String FLOAT_64 = "float64";

    /*
    int8, uint8, int16, uint16, int32, uint32, int64, uint64, float32, float64
     */



    public static final String VECTOR2 = "vector2f32";

    public static final String VECTOR3 = "vector3f32";

    public static final String VECTOR4 = "vector4f32";

    public static final String STRING = "string";

    public static final String RGB8 = "RGB8";

    public static final String RGBA8 = "RGBA8";

    public static final String ARRAY = "array";

    public static final String DICTIONARY  = "dictionary";

    //----------------------------------------------------
    // local
    private final String name;

    private T defaultValue;

    @JsonIgnore
    public Class<T> valueClass;

    //
    public TypeDefinition(final String _name, Class<T> _tClass) {

        name = _name;
        valueClass = _tClass;
    }

    public String getName() {

        return name;
    }

    public T getDefaultValue() {

        return defaultValue;
    }

    public void setDefaultValue(final T _defaultValue) {

        defaultValue = _defaultValue;
    }

    private Class<T> getValueClass() {

        return valueClass;
    }

    private void setValueClass(final Class<T> _valueClass) {

        valueClass = _valueClass;
    }

    public Parameter<T> createParameter(final String _id) {
        if (_id != null) {
            return new Parameter<>(_id, this);
        }

        return null;
    }
}

