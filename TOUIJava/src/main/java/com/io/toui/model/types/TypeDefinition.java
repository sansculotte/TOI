package com.io.toui.model.types;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by inx on 29/11/16.
 */
public abstract class TypeDefinition<T> {

    //----------------------------------------------------
    // static
    public static final String BOOLEAN = "boolean";

    public static final String NUMBER = "number";

    public static final String VECTOR2 = "vector2";

    public static final String VECTOR3 = "vector3";

    public static final String VECTOR4 = "vector4";

    public static final String STRING = "string";

    public static final String COLOR = "color";

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
}

