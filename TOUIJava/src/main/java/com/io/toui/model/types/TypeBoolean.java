package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class TypeBoolean extends TypeDefinition<Boolean> {

    enum Behaviour {
        Toggle, Bang, Press
    }

    Behaviour behaviour = Behaviour.Toggle;

    public TypeBoolean() {

        super(BOOLEAN);
    }
}
