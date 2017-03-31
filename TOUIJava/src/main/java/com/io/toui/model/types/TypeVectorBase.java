package com.io.toui.model.types;

public abstract class TypeVectorBase<T extends VectorBase> extends TypeDefinition<T> {

    private Number precision;

    private T min;

    private T max;

    private Number step;

    private String unit;

    private boolean cyclic;

    private boolean pow2;


    public TypeVectorBase(Class<T> _tClass) {
        super(NUMBER, _tClass);
    }

    public TypeVectorBase(final String _name, Class<T> _tClass) {
        super(_name, _tClass);
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
