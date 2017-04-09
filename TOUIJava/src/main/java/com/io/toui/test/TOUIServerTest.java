package com.io.toui.test;

import com.io.toui.model.ICommands.Update;
import com.io.toui.model.Parameter;
import com.io.toui.model.TOUIServer;
import com.io.toui.model.types.*;
import com.io.toui.test.serializer.JsonSerializer;
import com.io.toui.test.websocket.server.WebsocketServerTransporterNetty;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.HashMap;

/**
 * Created by inx on 29/11/16.
 */
public class TOUIServerTest implements Update {

    //------------------------------------------------------------
    //
    public static void main(final String[] args) {

        try {
            final TOUIServerTest test = new TOUIServerTest();

//            while (true) {
//
//                try {
//                    Thread.sleep(300);
//
//                    test.updateVar1();
//
//                    Thread.sleep(200);
//
//                    test.updateVar2();
//
////                    Thread.sleep(10000);
////
////                    test.dumpCache();
//
//                }
//                catch (final InterruptedException _e) {
//                    break;
//                }
//            }
        }
        catch (final InterruptedException | CertificateException | IOException _e) {
            _e.printStackTrace();
        }

        System.out.println("finish server");
    }


    //------------------------------------------------------------
    //
    private final TOUIServer toui;

    private final Parameter<Number> theValueLong;

    private final Parameter<String> theValueString;

    private final Parameter<Number> theValueDouble;

    private final Parameter<Number> theValueInt;

    private final Parameter<HashMap<String, Number>> theValueMap;

    private int counter = 0;

    //------------------------------------------------------------
    //
    public TOUIServerTest() throws IOException, CertificateException, InterruptedException {

        // create serializer and transporter
        final JsonSerializer serializer = new JsonSerializer();

//        final UDPServerTransporter transporter = new UDPServerTransporter(8181);
//        transporter.setTargetPort(61187);

//        final TCPServerTransporter transporter = new TCPServerTransporter(8888);
//        final WebsocketServerTransporter transporter = new WebsocketServerTransporter(8181);
        final WebsocketServerTransporterNetty transporter = new WebsocketServerTransporterNetty(8181);

        // create toui
        toui = new TOUIServer(serializer, transporter);
        toui.setUpdateListener(this);

        // create values
        theValueLong = new Parameter<>("1", new TypeNumber());
        theValueLong.value = 123L;
        theValueLong.description = "long";

        theValueString = new Parameter<>("2", new TypeString());
        theValueString.value = "oi. jep updates kommen. jeder char einzeln?? oder hast du " +
                               "tatsächlich nach jedem tip enter gedrückt?";
        theValueString.description = "test description 2";
        theValueString.label = "labeling";
        theValueString.userdata = "some user data?";

        theValueDouble = new Parameter<>("3", new TypeNumber());
        theValueDouble.value = 3.1415926535897932384626433832795028841971693993751D;
        theValueDouble.description = "pi";

        theValueInt = new Parameter<>("4", new TypeNumber());
        theValueInt.value = 3;
        theValueInt.description = "int";

        TypeNumber dictValue = new TypeNumber();
        dictValue.setMin(0);
        dictValue.setMax(10);
        dictValue.setStep(2);

        theValueMap = new Parameter<>("dict", new TypeDictionary<>(dictValue));
//        theValueMap.value = new HashMap<>();
        theValueMap.value.put("key1", 123);
        theValueMap.description = "a dictionary";

        // added values to toui
        toui.add(theValueLong);
        toui.add(theValueString);
        toui.add(theValueDouble);
        toui.add(theValueInt);
//        toui.add(theValueMap);

        toui.operateOnCache(valueCache -> {});
    }

    public void updateVar1() {

        Parameter<Number> newVal = theValueLong.cloneEmpty();
        newVal.value = theValueLong.value.longValue() + 1;

        toui.update(newVal);
    }

    public void updateVar2() {
        Parameter<String> newVal = theValueString.cloneEmpty();

        newVal.value = "content: " + counter++;
        toui.update(newVal);
    }

    public void dumpCache() {
    }

    //------------------------------------------------------------
    //
    @Override
    public void updated(final Parameter<?> _value) {
        // updated from client
        System.out.println("server: updated: " + _value.id + " : " + _value.value);

        // lookup value and updated it

        // updated cache and all clients
        toui.update(_value);

        toui.dumpCache();
    }
}
