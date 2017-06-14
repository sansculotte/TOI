package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public abstract class TypeNumberBase<T extends Number> extends TypeDefinition<T> {

    enum ScaleFunc {
        LINEAR,
        LOG,
        EXP
    }

    private static String typeToString(final TypeDefinition.numtypes _type) {
        switch (_type) {
            case INT_8:
                return TypeDefinition.INT_8;
            case INT_16:
                return TypeDefinition.INT_16;
            case INT_32:
                return TypeDefinition.INT_32;
            case INT_64:
                return TypeDefinition.INT_64;
            case UINT_8:
                return TypeDefinition.UINT_8;
            case UINT_16:
                return TypeDefinition.UINT_16;
            case UINT_32:
                return TypeDefinition.UINT_32;
            case UINT_64:
                return TypeDefinition.UINT_64;
            case FLOAT_32:
                return TypeDefinition.FLOAT_32;
            case FLOAT_64:
                return TypeDefinition.FLOAT_64;
        }

        return TypeDefinition.INT_32;
    }

    private static String classToDefault(Class<?> _tClass) {

        if (_tClass.equals(Integer.class)) {
            return TypeDefinition.INT_32;
        }

        if (_tClass.equals(Long.class)) {
            return TypeDefinition.INT_64;
        }

        if (_tClass.equals(Float.class)) {
            return TypeDefinition.FLOAT_32;
        }

        if (_tClass.equals(Double.class)) {
            return TypeDefinition.FLOAT_64;
        }

        // ??
        return TypeDefinition.FLOAT_64;
    }

    private T min;

    private T max;

    private Number multipleof;

    private ScaleFunc scale;

    private String unit;

    public TypeNumberBase(Class<T> _tClass) {
        super(classToDefault(_tClass), _tClass);
    }

    public TypeNumberBase(final TypeDefinition.numtypes _type, Class<T> _tClass) {
        super(typeToString(_type), _tClass);
    }

    private TypeNumberBase(final String _name, Class<T> _tClass) {
        super(_name, _tClass);
    }

    public Number getMultipleof() {

        return multipleof;
    }

    public void setMultipleof(final Number _value) {

        multipleof = _value;
    }

    public T getMin() {

        return min;
    }

    public void setMin(final T _min) {

        min = _min;
    }

    public T getMax() {

        return max;
    }

    public void setMax(final T _max) {

        max = _max;
    }

    public String getUnit() {

        return unit;
    }

    public void setUnit(final String _unit) {

        unit = _unit;
    }
}
