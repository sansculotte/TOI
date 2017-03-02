package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public abstract class TypeNumberBase<T extends Number> extends TypeDefinition<T> {

    private Number precision;

    private T min;

    private T max;

    private Number step;

    private String unit;

    private boolean cyclic;

    private boolean pow2;


    public TypeNumberBase() {
        super(NUMBER);
    }

    public TypeNumberBase(final String _name) {
        super(_name);
    }

    public Number getPrecision() {

        return precision;
    }

    public void setPrecision(final int _precision) {

        precision = _precision;
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

    public Number getStep() {

        return step;
    }

    public void setStep(final double _step) {

        step = _step;
    }

    public String getUnit() {

        return unit;
    }

    public void setUnit(final String _unit) {

        unit = _unit;
    }

    public boolean isCyclic() {

        return cyclic;
    }

    public void setCyclic(final boolean _cyclic) {

        cyclic = _cyclic;
    }

    public boolean isPow2() {

        return pow2;
    }

    public void setPow2(final boolean _pow2) {

        pow2 = _pow2;
    }
}
