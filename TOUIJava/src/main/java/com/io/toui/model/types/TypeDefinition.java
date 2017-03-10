package com.io.toui.model.types;

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

    //
    public TypeDefinition(final String _name) {

        name = _name;
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
}

