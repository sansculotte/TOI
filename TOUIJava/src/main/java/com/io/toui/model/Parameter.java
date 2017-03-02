package com.io.toui.model;

import com.io.toui.model.types.TypeDefinition;

/**
 * Created by inx on 02/03/17.
 */
public class Parameter<T> {

    // mandatory
    public String id;

    public TypeDefinition<T> type;

    // optional
    public T value;

    public String group;

    public Number order;

    public String description;

    public String label;

    // Widget widget;

    public Object userdata;

    public Parameter() {
    }

    public Parameter(final String _id, final TypeDefinition<T> _type) {
        id = _id;
        type = _type;
    }

    public Parameter<T> cloneEmpty() {

        // only id and value
        final Parameter<T> newDesc = new Parameter<>(id, type);

        return newDesc;
    }

    public void update(final Parameter<?> _other) {

        if (!id.equals(_other.id)) {
            System.err.println("dont updated unmatching id");
            return;
        }

        if ((_other.type != null) && !type.equals(_other.type)) {
            System.err.println("not updated unmatching types");
            return;
        }

        // try our best to match the values
        if (_other.value != null) {

            try {
                value = (T)value.getClass().cast(_other.value);
            }
            catch (final ClassCastException e) {

                if ((value instanceof Number) && (_other.value instanceof Number)) {

                    if (value instanceof Integer) {
                        value = (T)new Integer(((Number)_other.value).intValue());
                    }
                    else if (value instanceof Short) {
                        value = (T)new Short(((Number)_other.value).shortValue());
                    }
                    else if (value instanceof Byte) {
                        value = (T)new Byte(((Number)_other.value).byteValue());
                    }
                    else if (value instanceof Long) {
                        value = (T)new Long(((Number)_other.value).longValue());
                    }
                    else if (value instanceof Float) {
                        value = (T)new Float(((Number)_other.value).floatValue());
                    }
                    else if (value instanceof Double) {
                        value = (T)new Double(((Number)_other.value).doubleValue());
                    }

                } else {
                    System.err.println("cannot updated of from type");
                }
            }
        }

        final Parameter<T> other = (Parameter<T>)_other;

        if (other.description != null) {
            description = other.description;
        }

        if (other.label != null) {
            label = other.label;
        }

        if (other.userdata != null) {
            userdata = other.userdata;
        }
    }

    public void dump() {

        System.out.println("id:\t\t\t\t" + id);
        System.out.println("type:\t\t\t" + type);
        System.out.println("value:\t\t\t" + value);
        System.out.println("label:\t\t\t" + label);
        System.out.println("description:\t" + description);
        System.out.println("userdata:\t\t" + userdata);

    }
}
