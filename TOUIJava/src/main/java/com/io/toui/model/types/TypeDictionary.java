package com.io.toui.model.types;

import java.util.HashMap;

/**
 * Created by inx on 02/03/17.
 */
public class TypeDictionary<T> extends TypeDefinition<HashMap<String, T>> {

    public TypeDefinition<?> value;

    public TypeDictionary() {

        this(null);
    }

    public TypeDictionary(final TypeDefinition<T> _valueDef) {

        super(DICTIONARY, (Class<HashMap<String, T>>)(Object)HashMap.class);

        value = _valueDef;
    }
}
