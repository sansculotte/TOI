package com.io.toui.model;

/**
 * Created by inx on 29/11/16.
 */
public class ValueDescription<T> {

    //------------------------------------------------------------
    //
    // mandatory
    public String id;

    public T value;

    public String type;

    private T _default;

    public String description;

    public String label;

    public Object userdata;

    //------------------------------------------------------------
    //
    public ValueDescription() {
    }

    public ValueDescription(final String _id, final String _type)
    {
        id = _id;
        type = _type;
    }


    //------------------------------------------------------------
    //
    private String getId() {

        return id;
    }

    public ValueDescription<T> cloneEmpty() {

        final ValueDescription<T> newDesc = new ValueDescription<>();

        newDesc.id = id;
        newDesc.value = value;

        return newDesc;
    }
}

