package com.io.toui.test.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.io.toui.model.types.*;

import java.io.IOException;

/**
 * Created by inx on 02/03/17.
 */
public class TypeDefinitionDeserializer extends StdDeserializer<TypeDefinition<?>> {

    public TypeDefinitionDeserializer() {

        this(null);
    }

    protected TypeDefinitionDeserializer(final Class<?> vc) {

        super(vc);
    }

    @Override
    public TypeDefinition<?> deserialize(
            final JsonParser p, final DeserializationContext ctxt) throws
                                                                   IOException,
                                                                   JsonProcessingException {

        final JsonNode node = p.getCodec().readTree(p);

        if (!node.has("name")) {
            return null;
        }

        final String name = node.get("name").asText();

        switch (name) {
            case TypeDefinition.BOOLEAN:
                return p.getCodec().treeToValue(node, TypeBoolean.class);
            case TypeDefinition.NUMBER:
                return p.getCodec().treeToValue(node, TypeNumber.class);
            case TypeDefinition.VECTOR2:
                return p.getCodec().treeToValue(node, TypeVector2.class);
            case TypeDefinition.VECTOR3:
                return p.getCodec().treeToValue(node, TypeVector3.class);
            case TypeDefinition.VECTOR4:
                return p.getCodec().treeToValue(node, TypeVector4.class);
            case TypeDefinition.STRING:
                return p.getCodec().treeToValue(node, TypeString.class);
            case TypeDefinition.COLOR:
                return p.getCodec().treeToValue(node, TypeColor.class);
            case TypeDefinition.ARRAY:
                return p.getCodec().treeToValue(node, TypeArray.class);
            case TypeDefinition.DICTIONARY:
                return p.getCodec().treeToValue(node, TypeDictionary.class);
        }

        return null;
    }
}
