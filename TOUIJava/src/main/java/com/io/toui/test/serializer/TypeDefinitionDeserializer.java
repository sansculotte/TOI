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
            case TypeDefinition.BOOLEAN: {
                final TypeBoolean type = p.getCodec().treeToValue(node, TypeBoolean.class);

                if (node.has("default")) {
                    type.setDefaultValue(node.get("default").booleanValue());
                }
                return type;
            }
            /*
            int8, uint8, int16, uint16, int32, uint32, int64, uint64, float32, float64
             */
            case TypeDefinition.INT_8:
            case TypeDefinition.UINT_8:
            case TypeDefinition.INT_16:
            case TypeDefinition.UINT_16:
            case TypeDefinition.INT_32:
            case TypeDefinition.UINT_32:
            {
                final TypeInteger type = p.getCodec().treeToValue(node, TypeInteger.class);

                if (node.has("default")) {
                    type.setDefaultValue(node.get("default").intValue());
                }

                if (node.has("subtype")) {
//                    type.setSubtype(node.get("subtype").asText());
                }

                if (node.has("min")) {
//                    type.setMin(node.get("min").numberValue());
                }

                if (node.has("max")) {
//                    type.setMax(node.get("max").numberValue());
                }

                if (node.has("multipleof")) {
                    type.setMultipleof(node.get("multipleof").numberValue());
                }

                if (node.has("unit")) {
                    type.setUnit(node.get("unit").asText());
                }

                return type;
            }
            case TypeDefinition.VECTOR2:
                return p.getCodec().treeToValue(node, TypeVector2.class);
            case TypeDefinition.VECTOR3:
                return p.getCodec().treeToValue(node, TypeVector3.class);
            case TypeDefinition.VECTOR4:
                return p.getCodec().treeToValue(node, TypeVector4.class);
            case TypeDefinition.STRING: {

                final TypeString type = p.getCodec().treeToValue(node, TypeString.class);

                if (node.has("default")) {
                    type.setDefaultValue(node.get("default").asText());
                }

                if (node.has("format")) {
//                    type.format = Format.valueOf(node.get("format").asText());
                }

                if (node.has("filemask")) {
//                    type.filemask = node.get("filemask").asText();
                }

                if (node.has("maxchars")) {
//                    type.maxchars = node.get("maxchars").asLong();
                }

                return type;
            }
//            case TypeDefinition.COLOR: {
//
//                final TypeColor type = p.getCodec().treeToValue(node, TypeColor.class);;
//
//                if (node.has("default")) {
//                    final Color c = Color.decode(node.get("default").asText());
//                    type.setDefaultValue(c);
//                }
//
//                return type;
//            }

            case TypeDefinition.ARRAY: {

                final TypeArray type = p.getCodec().treeToValue(node, TypeArray.class);

                if (node.has("default")) {

                }

                return type;
            }
            case TypeDefinition.DICTIONARY:
                return p.getCodec().treeToValue(node, TypeDictionary.class);
        }

        return null;
    }
}
