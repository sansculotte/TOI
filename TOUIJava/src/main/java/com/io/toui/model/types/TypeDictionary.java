package com.io.toui.model.types;

import java.util.Map;

/**
 * Created by inx on 02/03/17.
 */
public class TypeDictionary<T> extends TypeDefinition<Map<String, T>> {

    public TypeDefinition<?> value;

    public TypeDictionary() {

        this(null);
    }

    public TypeDictionary(final TypeDefinition<T> _valueDef) {

        super(DICTIONARY);

        System.out.println("Dict constructor, set value");

        value = _valueDef;
    }
}
