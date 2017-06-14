package com.io.toui.model;

import com.io.toui.model.types.TypeDefinition;

import java.util.*;

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

    public Parameter(T _value) {
        value = _value;

        // figure type??
    }

    public Parameter(final String _id, T _value, final TypeDefinition<T> _type) {
        id = _id;
        value = _value;
        type = _type;
    }

    public Parameter(final String _id, final TypeDefinition<T> _type) {
        id = _id;
        type = _type;


        try {
            value = _type.valueClass.newInstance();
        }
        catch (InstantiationException _e) {
//            _e.printStackTrace();
        }
        catch (IllegalAccessException _e) {
            _e.printStackTrace();
        }
    }


    public Parameter<T> cloneEmpty() {

        // only id and value
        final Parameter<T> newDesc = new Parameter<>(id, type);


        return newDesc;
    }

    public void set(final T _value) {
        value = _value;
    }

    public void setValue(Object _value) {

        try {

            if (type != null) {
                value = type.valueClass.cast(_value);
            } else if (value != null) {
                value = (T)value.getClass().cast(_value);
            } else {
                // not type-def, no value...?
            }

        }
        catch (final ClassCastException e) {

            if ((value instanceof Number) && (_value instanceof Number)) {

                if (value instanceof Integer) {

                    // check for unsigned?

                    value = (T)new Integer(((Number)_value).intValue());
                }
                else if (value instanceof Short) {
                    value = (T)new Short(((Number)_value).shortValue());
                }
                else if (value instanceof Byte) {
                    value = (T)new Byte(((Number)_value).byteValue());
                }
                else if (value instanceof Long) {
                    value = (T)new Long(((Number)_value).longValue());
                }
                else if (value instanceof Float) {
                    value = (T)new Float(((Number)_value).floatValue());
                }
                else if (value instanceof Double) {
                    value = (T)new Double(((Number)_value).doubleValue());
                }

            }
            else if (value instanceof Map) {

                if (_value instanceof Map) {

                    ((Map)value).clear();


                    ((Map)_value).forEach((_o, _o2) -> {
                        ((Map)value).put(_o, _o2);
                    });

                    //                        System.out.println("updated map");

                    //                            value = (Map)_other.value;

                } else {
                    System.err.println("other not of map");
                }


            }
            else {
                System.err.println("cannot updated of from type: " + value.getClass().getName
                        () + " other: " + _value.getClass().getName());
            }
        }

    }

    public void update(final Parameter<?> _other) {

        if (!Objects.equals(id, _other.id)) {
            System.err.println("don't updated unmatching id");
            return;
        }

        if ((_other.type != null) && !Objects.equals(type.getName(), _other.type.getName())) {
            System.err.println("not updated unmatching types: " + type + " != " + _other.type );
            return;
        }

        // try our best to match the values
        if (_other.value != null) {

            setValue(_other.value);
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

        System.out.println("--- " + id + " ---");
//        System.out.println("id:\t\t\t\t" + id);
        System.out.println("type:\t\t\t" + type.getName());
        System.out.println("value:\t\t\t" + value);
        System.out.println("label:\t\t\t" + label);
        System.out.println("description:\t" + description);
        System.out.println("userdata:\t\t" + userdata);
        System.out.println();

    }
}
