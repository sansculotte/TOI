package com.io.toui.test.serializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.LongNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.io.toui.model.*;

import java.io.IOException;

/**
 * Created by inx on 02/03/17.
 */
public class PacketDeserializer extends StdDeserializer<Packet> {

    public PacketDeserializer() {

        this(null);
    }

    public PacketDeserializer(final Class<?> vc) {

        super(vc);
    }

    @Override
    public Packet deserialize(
            final JsonParser p, final DeserializationContext ctxt) throws
                                                                   IOException,
                                                                   JsonProcessingException {

        JsonNode node = p.getCodec().readTree(p);

        if (!node.has("command")) {
            // error
        }

        String command = node.get("command").asText();

        long timestamp = 0;
        if (node.has("timestamp")) {
            timestamp = (Integer)((LongNode)node.get("timestamp")).numberValue();
        }

        String packetId = "";
        if (node.has("id")) {
            packetId = node.get("id").asText();
        }


        Object data = null;
        if (node.has("data")) {

            if (command.equals(ICommands.ADD) || command.equals(ICommands.UPDATE) || command.equals
                    (ICommands.REMOVE)) {
                // we expect a Parameter<?>


                ObjectNode n = (ObjectNode)node.get("data");

                data = p.getCodec().treeToValue(n, Parameter.class);

            } else {
                // we expect something else
            }
        } else {
            // error no data
        }


        Packet packet = new Packet(command, data, timestamp, packetId);

        return packet;
    }
}
