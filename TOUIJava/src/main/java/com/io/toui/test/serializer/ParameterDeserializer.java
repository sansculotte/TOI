package com.io.toui.test.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.io.toui.model.Parameter;

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



        return null;
    }
}
