package com.io.toui.model.types;

import java.util.List;

/**
 * Created by inx on 02/03/17.
 */
public class TypeArray extends TypeDefinition<List<?>> {

    TypeDefinition subtype;

    public TypeArray() {

        super(ARRAY);
    }
}
