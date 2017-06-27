package com.io.toi.model;

/**
 * Created by inx on 21/06/17.
 */
public class ToiParameterNumber<T extends Number> extends ToiParameter<T>  {

    public ToiParameterNumber(final int _id, final ToiTypeDefinition<T> _type) {

        super(_id, _type);
    }
}
