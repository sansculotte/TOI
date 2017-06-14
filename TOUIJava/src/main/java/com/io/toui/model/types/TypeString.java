package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class TypeString extends TypeDefinition<String> {

    public TypeString() {

        super(STRING, String.class);

        setDefaultValue("");
    }
}
