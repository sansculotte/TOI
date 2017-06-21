package com.io.toui.model.types;

import com.io.toui.model.*;
import com.io.toui.model.ToiTypes.TinyString;
import com.io.toui.model.ToiTypes.TouiDatatypes;
import com.io.toui.model.ToiTypes.TouiScale;
import com.io.toui.model.ToiTypes.TouiTypedef;
import io.kaitai.struct.KaitaiStream;
import com.io.toui.model.exceptions.ToiDataErrorExcpetion;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ToiTypeNumber<T extends Number> extends ToiTypeDefinition<T> {

    public static void parseOption(
            final ToiTypeNumber<?> _typedef,
            final TouiTypedef _dataid,
            final KaitaiStream _io) throws ToiDataErrorExcpetion {
        switch (_dataid) {

            case SCALE:
                _typedef.setScale(TouiScale.byId(_io.readU1()));
                break;

            case UNIT:
                final TinyString tinyString = new TinyString(_io);
                _typedef.setUnit(tinyString.data());
                break;

            case SUBTYPE:
            case ENTRIES:
                // not a number data id!!
                throw new ToiDataErrorExcpetion();
        }
    }

    //----------------------------------------------------
    private T min;

    private T max;

    private T multipleof;

    private TouiScale scale;

    private String unit;

    //----------------------------------------------------
    public ToiTypeNumber(final TouiDatatypes _typeid) {

        super(_typeid);
    }

    public ToiTypeNumber(
            final TouiDatatypes _typeid,
            final T _min,
            final T _max,
            final T _multipleof) {

        super(_typeid);
        min = _min;
        max = _max;
        multipleof = _multipleof;
    }

    @Override
    public void write(final OutputStream _outputStream) throws IOException {

        super.write(_outputStream);

        if (getMin() != null) {
            _outputStream.write((int)TouiTypedef.MIN.id());
            writeValue(min, _outputStream);
        }

        if (getMax() != null) {
            _outputStream.write((int)TouiTypedef.MAX.id());
            writeValue(max, _outputStream);
        }

        if (getMultipleof() != null) {
            _outputStream.write((int)TouiTypedef.MULT.id());
            writeValue(multipleof, _outputStream);
        }

        if (scale != null) {
            _outputStream.write((int)TouiTypedef.SCALE.id());
            _outputStream.write((int)scale.id());
        }

        if (unit != null) {
            _outputStream.write((int)TouiTypedef.UNIT.id());
            ToiParser.writeTinyString(unit, _outputStream);
        }
    }

    //----------------------------------------------------
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

    public T getMultipleof() {

        return multipleof;
    }

    public void setMultipleof(final T _multipleof) {

        multipleof = _multipleof;
    }

    public TouiScale getScale() {

        return scale;
    }

    public void setScale(final TouiScale _scale) {

        scale = _scale;
    }

    public String getUnit() {

        return unit;
    }

    public void setUnit(final String _unit) {

        unit = _unit;
    }

}
