package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class TypeLong extends TypeNumberBase<Long> {

    private static numtypes convertType(longtypes _type) {

        switch (_type) {
            case INT_64:
                return numtypes.INT_64;
            case UINT_64:
                return numtypes.UINT_64;
        }

        return numtypes.INT_64;
    }


    public TypeLong(final longtypes _type) {
        super(convertType(_type), Long.class);
    }
}
