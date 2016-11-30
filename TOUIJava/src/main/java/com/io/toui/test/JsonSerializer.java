package com.io.toui.test;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.io.toui.model.*;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by inx on 29/11/16.
 */
public class JsonSerializer implements ITOUISerializer {

    final ObjectMapper mapper = new ObjectMapper();

    public JsonSerializer() {

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // do not serialize NULL map values...
        mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        // do not serialize NULL objects
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // to allow (non-standard) unquoted field names in JSON:
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // to allow use of apostrophes (single quotes), non standard
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public byte[] serialize(final Packet _packet) {

        try {
            final String jsonString = mapper.writeValueAsString(_packet);
            return jsonString.getBytes(Charset.forName("UTF-8"));
        }
        catch (final JsonProcessingException _e) {
            _e.printStackTrace();
            return null;
        }
    }

    @Override
    public Packet deserialize(final byte[] _data) {

        String json = new String(_data, Charset.forName("UTF-8"));

        try {
            final Packet aPacket = mapper.readValue(json, Packet.class);
            return aPacket;
        }
        catch (IOException _e) {
            _e.printStackTrace();
            return null;
        }
    }

    @Override
    public ValueDescription<?> convertToValueDescription(final Object _o) {

        return mapper.convertValue(_o, ValueDescription.class);
    }
}
