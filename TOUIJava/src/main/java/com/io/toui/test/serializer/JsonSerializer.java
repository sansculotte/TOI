package com.io.toui.test.serializer;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.io.toui.model.*;
import com.io.toui.model.types.*;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created by inx on 29/11/16.
 */
public class JsonSerializer implements ITOUISerializer {

    public static void main(String[] args) {

//        Packet<Parameter<?>> packet = new Packet<>();
//
//        packet.command = ICommands.ADD;
//        packet.id = "packet id";
//
//        TypeNumber doubleType = new TypeNumber();
//        doubleType.setCyclic(true);
//        doubleType.setMax(100);
//        doubleType.setMin(0.01);
//        doubleType.setDefaultValue(10.1010);
//
//        Parameter<Number> numberParameter = new Parameter<>("parm id", doubleType);
//        numberParameter.value = 3.1415926535897932384626433832795028841971693993751;
//        numberParameter.group = "testgroup";
//
//
//        TypeString stringType = new TypeString();
//        stringType.format = TypeString.Format.IP;
//        stringType.setDefaultValue("mydefault");
//        Parameter<String> strParam = new Parameter<>("string id", stringType);
//
//        strParam.value = "my value blabla";
//        strParam.description = "a description for this string value";
//        strParam.group = "groupA";
//        strParam.label = "a string";
//        strParam.order = 3;
//
//        packet.data = numberParameter;
//
////        packet.data = new Parameter<>("parameter id 1", new TypeString());
////        ((TypeString)packet.data.type).filemask = "mask";
//
//        JsonSerializer serializer = new JsonSerializer();
//        serializer.serialize(packet);

    }

//    public static void main(String[] args) {
//
////        String json = "{\"command\":\"added\",\"data\":{\"id\":\"string id\"," +
////                      "\"type\":{\"name\":\"string\",\"defaultValue\":\"mydefault\"," +
////                      "\"format\":\"IP\"},\"value\":\"my value blabla\",\"group\":\"groupA\"," +
////                      "\"order\":3,\"description\":\"a description for this string value\"," +
////                      "\"label\":\"a string\"},\"id\":\"packet id\"}";
//
//
////        String json = "{\n" +
////                      "    \"command\": \"added\",\n" +
////                      "    \"data\": {\n" +
////                      "        \"id\": \"parm id\",\n" +
////                      "        \"type\": {\n" +
////                      "            \"name\": \"number\",\n" +
////                      "            \"cyclic\": true,\n" +
////                      "            \"min\": 0.01,\n" +
////                      "            \"max\": 10\n" +
////                      "        },\n" +
////                      "        \"value\": 0.1,\n" +
////                      "        \"group\": \"testgroup\"\n" +
////                      "    },\n" +
////                      "    \"id\": \"packet id\"\n" +
////                      "}";
//
//        String json = "{\"command\":\"added\",\"data\":{\"id\":\"parm id\"," +
//                      "\"type\":{\"name\":\"number\",\"defaultValue\":10.101,\"min\":0.01," +
//                      "\"max\":100,\"cyclic\":true},\"value\":3.141592653589793," +
//                      "\"group\":\"testgroup\"},\"id\":\"packet id\"}";
//
//        JsonSerializer serializer = new JsonSerializer();
//
//        Packet p = serializer.deserialize(json.getBytes());
//
//        System.out.println("dd");
//
//        double min = ((TypeNumber)((Parameter<?>)p.data).type).getMin().doubleValue();
//        double max = ((TypeNumber)((Parameter<?>)p.data).type).getMax().doubleValue();
//
//        System.out.println("min: " + min);
//        System.out.println("max: " + max);
//
//    }





    final ObjectMapper mapper = new ObjectMapper();

    public JsonSerializer() {

        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // do not serialize NULL map values...
        mapper.disable(SerializationFeature.WRITE_NULL_MAP_VALUES);

        // do not serialize NULL, empty, default objects
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.setSerializationInclusion(Include.NON_EMPTY);
        mapper.setSerializationInclusion(Include.NON_DEFAULT);

        // to allow (non-standard) unquoted field names in JSON:
        mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // to allow use of apostrophes (single quotes), non standard
        mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
        mapper.configure(Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);
        mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);


        SimpleModule module = new SimpleModule();
        module.addDeserializer(Packet.class, new PacketDeserializer());
        module.addDeserializer(Parameter.class, new ParameterDeserializer());
        module.addDeserializer(TypeDefinition.class, new TypeDefinitionDeserializer());
        mapper.registerModule(module);

//        SimpleModule testModule = new SimpleModule("PacketModule",
//                                                   new Version(1,
//                                                               0,
//                                                               0,
//                                                               null,
//                                                               null,
//                                                               null));
//
//        testModule.addDeserializer(Object.class, new MyDeserializer(Object.class));
//
////        testModule.
//
//        mapper.registerModule(testModule);
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
    public Parameter<?> convertToParameter(final Object _o) {

        return mapper.convertValue(_o, Parameter.class);
    }
}
