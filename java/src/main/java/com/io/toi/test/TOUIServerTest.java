package com.io.toi.test;

import com.io.toi.model.*;
import com.io.toi.model.ICommands.Update;
import com.io.toi.model.types.*;
import com.io.toi.test.websocket.server.WebsocketServerTransporterNetty;

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
    private final TOUIServer toi;

//    private final ToiParameter<Number> theValueLong;
//
    private final ToiParameter<String> theValueString;

    private final ToiParameter<Double> theValueDouble;
//
    private final ToiParameter<Integer> theValueInt;

    private final ToiParameter<Boolean> theValueBool;

    private final ToiParameterNumber<Long> theValueLong;
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

        // create toi
        toi = new TOUIServer(transporter);
        toi.setUpdateListener(this);
        toi.setInitListener(this);

        // create values
//        theValueLong = new ToiParameter<>("1", new TypeNumber());
//        theValueLong.value = 123L;
//        theValueLong.description = "long";
//
        theValueString = new ToiParameter<>(1, new ToiTypeSTRING());
        theValueString.setValue("This is a text encoded in utf-8. let's test it: ¬”#£œæýýý‘");
        theValueString.setDescription("test description 2");
        theValueString.setLabel("text label");
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


        theValueLong = new ToiParameterNumber<>(5, new ToiTypeINT64());
        //theValueLong.setValue((long)10);
        theValueLong.setLabel("a long number");

//        TypeNumber dictValue = new TypeNumber();
//        dictValue.setMin(0);
//        dictValue.setMax(10);
//        dictValue.setStep(2);

//        theValueMap = new ToiParameter<>("dict", new TypeDictionary<>(dictValue));
////        theValueMap.value = new HashMap<>();
//        theValueMap.value.put("key1", 123);
//        theValueMap.description = "a dictionary";
//
//        // added values to toi
//        toi.add(theValueLong);
        toi.add(theValueString);
        toi.add(theValueDouble);
        toi.add(theValueInt);
        toi.add(theValueBool);

////        toi.add(theValueMap);
//
//        toi.operateOnCache(valueCache -> {});
    }

    public void updateVar1() {

        long v = theValueLong.getValue();

        ToiParameter<Long> newVal = theValueLong.cloneEmpty();
        newVal.setValue(theValueLong.getValue() + 1);

        toi.update(newVal);
    }

    public void updateVar2() {
        ToiParameter<String> newVal = theValueString.cloneEmpty();

        newVal.setValue("content: " + counter++);
        toi.update(newVal);
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
        toi.update(_value);

        toi.dumpCache();
    }

    @Override
    public void init() {

//        System.out.println("add another value");
//
//        ToiParameter<Boolean> boolVal = new ToiParameter<>(12, new ToiTypeBOOL());
//        boolVal.setDescription("a boolean value to test");
//        boolVal.setLabel("boolean label");
//
//        toi.add(boolVal);

//        try {
//            Thread.sleep(5000);
//        }
//        catch (InterruptedException _e) {
//            _e.printStackTrace();
//        }
//
//
//        theValueLong.setValue((long)123445);
//
//        toi.add(theValueLong);

    }
}
