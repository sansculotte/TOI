package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class TypeInteger extends TypeNumberBase<Integer> {

    private static numtypes convertType(inttypes _type) {

        switch (_type) {
            case UINT_32:
                return numtypes.UINT_32;
            case UINT_16:
                return numtypes.UINT_16;
            case UINT_8:
                return numtypes.UINT_8;
            case INT_32:
                return numtypes.INT_32;
            case INT_16:
                return numtypes.INT_16;
            case INT_8:
                return numtypes.INT_8;
        }

        return numtypes.INT_32;
    }


    public TypeInteger(final inttypes _type) {
        super(convertType(_type), Integer.class);
    }
}
