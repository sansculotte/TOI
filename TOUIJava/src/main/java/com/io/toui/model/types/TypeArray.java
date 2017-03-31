package com.io.toui.model.types;

import java.util.List;

/**
 * Created by inx on 02/03/17.
 */
public class TypeArray<T> extends TypeDefinition<List<T>> {

    TypeDefinition subtype;

    public TypeArray() {

        super(ARRAY, (Class<List<T>>)(Object)List.class);
    }
}
