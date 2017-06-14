package com.io.toui.test.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.io.toui.model.Parameter;
import com.io.toui.model.types.TypeDefinition;

import java.io.IOException;

/**
 * Created by inx on 02/03/17.
 */
public class ParameterDeserializer extends StdDeserializer<Parameter<?>> {

    public ParameterDeserializer() {
        this(null);
    }

    public ParameterDeserializer(final Class<?> vc) {

        super(vc);
    }

    @Override
    public Parameter<?> deserialize(
            final JsonParser p, final DeserializationContext ctxt) throws
                                                                   IOException,
                                                                   JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);

        // first deserialze id
        if (!node.has("id")) {
            // error, id is mandatory
            return null;
        }

        String id = node.get("id").asText();

        // deserialize typeDefinition
        TypeDefinition<?> typeDefinition = null;
        if (node.has("type") && node.get("type").isObject()) {

            final ObjectNode n = (ObjectNode)node.get("type");

            typeDefinition = p.getCodec().treeToValue(n, TypeDefinition.class);
        }

        // if we got a type def, we know what to do woth our value
        // what if we did not get any type-def?
        // -> lookup table?

        Parameter<?> parameter = null;

        if (typeDefinition != null) {
            //
            parameter = typeDefinition.createParameter(id);
        }

        if (node.has("value")) {
            final Object v = p.getCodec().treeToValue(node.get("value"), Object.class);

            if (parameter != null) {
                parameter.setValue(v);
            } else {
                parameter = new Parameter<>(v);
            }
        } else if (parameter == null) {
            parameter = new Parameter<>();
        }

        //
        if (node.has("group")) {
            parameter.group = node.get("group").asText();
        }

        if (node.has("order")) {
            parameter.order = node.get("order").numberValue();
        }

        if (node.has("description")) {
            parameter.description = node.get("description").asText();
        }

        if (node.has("label")) {
            parameter.description = node.get("label").asText();
        }

//        if (node.has("widget")) {
//            final ObjectNode n = (ObjectNode)node.get("widget");
//            parameter.widget = p.getCodec().treeToValue(n, Widget.class);
//        }

        if (node.has("userdata")) {
            parameter.userdata = p.getCodec().treeToValue(node.get("value"), Object.class);
        }

        return parameter;
    }
}
