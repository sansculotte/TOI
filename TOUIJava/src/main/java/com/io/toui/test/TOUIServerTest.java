package com.io.toui.test;

import com.io.toui.model.*;
import com.io.toui.model.ICommands.Update;
import com.io.toui.model.types.*;
import com.io.toui.test.websocket.server.WebsocketServerTransporterNetty;

import java.io.IOException;
import java.security.cert.CertificateException;

public class TOUIServerTest implements Update, ICommands.Init {

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

//    private final ToiParameter<Number> theValueLong;
//
    private final ToiParameter<String> theValueString;

    private final ToiParameter<Double> theValueDouble;
//
    private final ToiParameter<Integer> theValueInt;

    private final ToiParameter<Boolean> theValueBool;
//
//    private final ToiParameter<HashMap<String, Number>> theValueMap;

    private int counter;

    //------------------------------------------------------------
    //
    public TOUIServerTest() throws IOException, CertificateException, InterruptedException {


//        final UDPServerTransporter transporter = new UDPServerTransporter(8181);
//        transporter.setTargetPort(61187);

//        final TCPServerTransporter transporter = new TCPServerTransporter(8888);
//        final WebsocketServerTransporter transporter = new WebsocketServerTransporter(8181);
        final WebsocketServerTransporterNetty transporter = new WebsocketServerTransporterNetty
                (10000);

        // create toui
        toui = new TOUIServer(transporter);
        toui.setUpdateListener(this);
        toui.setInitListener(this);

        // create values
//        theValueLong = new ToiParameter<>("1", new TypeNumber());
//        theValueLong.value = 123L;
//        theValueLong.description = "long";
//
        theValueString = new ToiParameter<>(1, new ToiTypeSTRING());
        theValueString.setValue("oi. jep updates kommen. jeder char einzeln?? oder hast du " +
                               "tatsächlich nach jedem tip enter gedrückt?");
        theValueString.setDescription("test description 2");
        theValueString.setLabel("labeling");
        theValueString.setUserdata("some user data?".getBytes());

        theValueDouble = new ToiParameter<>(2, new ToiTypeFLOAT64(0.D, 10.D));
        theValueDouble.setLabel("a double");
        theValueDouble.setDescription("double description");
        theValueDouble.setValue(3.14);


        theValueInt = new ToiParameter<>(3, new ToiTypeINT32());
        theValueInt.setLabel("INT LABEL");
        theValueInt.setValue(333);


        theValueBool = new ToiParameter<>(4, new ToiTypeBOOL());
        theValueBool.setLabel("toggle button");
        theValueBool.setValue(true);

//        TypeNumber dictValue = new TypeNumber();
//        dictValue.setMin(0);
//        dictValue.setMax(10);
//        dictValue.setStep(2);

//        theValueMap = new ToiParameter<>("dict", new TypeDictionary<>(dictValue));
////        theValueMap.value = new HashMap<>();
//        theValueMap.value.put("key1", 123);
//        theValueMap.description = "a dictionary";
//
//        // added values to toui
//        toui.add(theValueLong);
        toui.add(theValueString);
        toui.add(theValueDouble);
        toui.add(theValueInt);
        toui.add(theValueBool);
////        toui.add(theValueMap);
//
//        toui.operateOnCache(valueCache -> {});
    }

    public void updateVar1() {

//        ToiParameter<Number> newVal = theValueLong.cloneEmpty();
//        newVal.value = theValueLong.value.longValue() + 1;
//
//        toui.update(newVal);
    }

    public void updateVar2() {
//        ToiParameter<String> newVal = theValueString.cloneEmpty();
//
//        newVal.value = "content: " + counter++;
//        toui.update(newVal);
    }

    public void dumpCache() {
    }

    //------------------------------------------------------------
    //
    @Override
    public void updated(final ToiParameter<?> _value) {
        // updated from client
        System.out.println("server: updated: " + _value.getId() + " : " + _value.getValue());

        // lookup value and updated it

        // updated cache and all clients
        toui.update(_value);

        toui.dumpCache();
    }

    @Override
    public void init() {

//        System.out.println("add another value");
//
//        ToiParameter<Boolean> boolVal = new ToiParameter<>(12, new ToiTypeBOOL());
//        boolVal.setDescription("a boolean value to test");
//        boolVal.setLabel("boolean label");
//
//        toui.add(boolVal);
    }
}
