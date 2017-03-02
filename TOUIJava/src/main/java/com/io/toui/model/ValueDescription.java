package com.io.toui.model;

/**
 * Created by inx on 29/11/16.
 */
public class ValueDescription<T> {

    //------------------------------------------------------------
    //
    // mandatory
    public final String id;

    public T value;

    // optional
    final public String type;

    private T _default;

    public String description;

    public String label;

    public Object userdata;

    //------------------------------------------------------------
    //
    public ValueDescription() {
        this(null, null);
    }

    public ValueDescription(final String _id) {
        this(_id, ValueTypes.STRING);
    }

    public ValueDescription(final String _id, final String _type)
    {
        id = _id;
        type = _type;
    }

    public ValueDescription(final String _id, final String _type, final T _value)
    {
        id = _id;
        type = _type;
        value = _value;
    }

    //------------------------------------------------------------
    //
    private String getId() {

        return id;
    }

    public ValueDescription<T> cloneEmpty() {

        // only id and valie
        final ValueDescription<T> newDesc = new ValueDescription<>(id, null, value);

        return newDesc;
    }

    public void update(ValueDescription<?> _other) {

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
            catch (ClassCastException e) {

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

        final ValueDescription<T> other = (ValueDescription<T>)_other;


        if (other._default != null) {
            _default = other._default;
        }

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

