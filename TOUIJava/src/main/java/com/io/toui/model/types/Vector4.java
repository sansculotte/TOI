package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class Vector4 extends Number {

    double[] value = { 0.D, 0.D, 0.D };

    public Double get0() {

        return value[0];
    }

    public double get1() {

        return value[1];
    }

    public double get2() {

        return value[2];
    }

    public double get3() {

        return value[3];
    }

    public void set0(final double _v) {

        value[0] = _v;
    }

    public void set1(final double _v) {

        value[1] = _v;
    }

    public void set2(final double _v) {

        value[2] = _v;
    }

    public void set3(final double _v) {

        value[3] = _v;
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
