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

            boolean doAutoUpdate = false;

            if (doAutoUpdate) {
                // some automatic value update...
                while (true) {

                    try {
                        Thread.sleep(300);
                        test.updateVar1();

                        Thread.sleep(200);
                        test.updateVar2();
                    }
                    catch (final InterruptedException _e) {
                        break;
                    }
                }
            }
        }
        catch (final InterruptedException | CertificateException | IOException _e) {
            _e.printStackTrace();
        }
    }


    //------------------------------------------------------------
    //
    private final TOUIServer toi;

    private final ToiParameter<String> theValueString;

    private final ToiParameter<Double> theValueDouble;

    private final ToiParameter<Integer> theValueInt;

    private final ToiParameter<Boolean> theValueBool;

    private final ToiParameterNumber<Long> theValueLong;

    private int counter;

    //------------------------------------------------------------
    //
    public TOUIServerTest() throws IOException, CertificateException, InterruptedException {


        // a udo transporter
//        final UDPServerTransporter transporter = new UDPServerTransporter(8181);
//        transporter.setTargetPort(61187);

        // a tcp transporter
//        final TCPServerTransporter transporter = new TCPServerTransporter(8888);

        // a websocket transporter
        final WebsocketServerTransporterNetty transporter = new WebsocketServerTransporterNetty
                (10000);

        // create toi
        toi = new TOUIServer(transporter);
        toi.setUpdateListener(this);
        toi.setInitListener(this);

        // create values
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
        theValueLong.setValue((long)10);
        theValueLong.setLabel("a long number");


        // add the values to toi
        toi.add(theValueString);
        toi.add(theValueDouble);
        toi.add(theValueInt);
        toi.add(theValueBool);
    }

    public void updateVar1() {

        ToiParameter<Long> newVal = theValueLong.cloneEmpty();
        newVal.setValue(theValueLong.getValue() + 1);

        toi.update(newVal);
    }

    public void updateVar2() {
        ToiParameter<String> newVal = theValueString.cloneEmpty();

        newVal.setValue("content: " + counter++);
        toi.update(newVal);
    }

    //------------------------------------------------------------
    //
    @Override
    public void updated(final ToiParameter<?> _value) {

        // updated from client
        System.out.println("server: updated: " + _value.getId() + " : " + _value.getValue());

        // lookup value and updated it in host app

        // updated cache and all clients
        // TODO: updating clients should be done inside toi
        toi.update(_value);

        toi.dumpCache();
    }

    @Override
    public void init() {
    }
}
