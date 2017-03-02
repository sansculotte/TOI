package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class Vector2 extends Number{

    double[] value = {0.D, 0.D};

    public Double get0() {
        return value[0];
    }

    public Double get1() {
        return value[1];
    }

    public void set0(final double _v) {
        value[0] = _v;
    }
    public void set1(final double _v) {
        value[1] = _v;
    }

    @Override
    public int intValue() {

        return get0().intValue();
    }

    @Override
    public long longValue() {

        return get0().longValue();
    }

    @Override
    public float floatValue() {

        return get0().floatValue();
    }

    @Override
    public double doubleValue() {

        return get0();
    }
}