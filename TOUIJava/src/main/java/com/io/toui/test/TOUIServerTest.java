package com.io.toui.test;

import com.io.toui.model.ICommands.Update;
import com.io.toui.model.*;
import com.io.toui.test.websocket.WebsocketServerTransporter;

import java.io.IOException;
import java.net.SocketException;

/**
 * Created by inx on 29/11/16.
 */
public class TOUIServerTest implements Update {

    //------------------------------------------------------------
    //
    public static void main(final String[] args) {

        try {
            final TOUIServerTest test = new TOUIServerTest();

            while (true) {

                try {
//                    Thread.sleep(3000);
//
//                    test.updateVar1();
//
//                    Thread.sleep(2000);
//
//                    test.updateVar2();

                    Thread.sleep(10000);

                    test.dumpCache();

                }
                catch (final InterruptedException _e) {
                    break;
                }
            }
        }
        catch (final SocketException _e) {
            _e.printStackTrace();
        }
        catch (final IOException _e) {
            _e.printStackTrace();
        }

        System.out.println("finish server");
    }


    //------------------------------------------------------------
    //
    private final TOUIServer toui;

    private final ValueDescription<Long> theValueLong;

    private final ValueDescription<String> theValueString;

    private final ValueDescription<Double> theValueDouble;

    private final ValueDescription<Integer> theValueInt;

    private int counter = 0;

    //------------------------------------------------------------
    //
    public TOUIServerTest() throws IOException {

        // create serializer and transporter
        final JsonSerializer serializer = new JsonSerializer();

//        final UDPServerTransporter transporter = new UDPServerTransporter(8888);
//        final TCPServerTransporter transporter = new TCPServerTransporter(8888);
        final WebsocketServerTransporter transporter = new WebsocketServerTransporter(8888);

        // create toui
        toui = new TOUIServer(serializer, transporter);
        toui.setUpdateListener(this);

        // create values
        theValueLong = new ValueDescription<>("1", ValueTypes.NUMBER);
        theValueLong.value = 123L;
        theValueLong.description = "long";

        theValueString = new ValueDescription<>("2", ValueTypes.STRING);
        theValueString.value = "oi. jep updates kommen. jeder char einzeln?? oder hast du " +
                               "tatsächlich nach jedem tip enter gedrückt?";
        theValueString.description = "test description 2";
        theValueString.label = "labeling";
        theValueString.userdata = "some user data?";

        theValueDouble = new ValueDescription<>("3", ValueTypes.NUMBER);
        theValueDouble.value = 3.1415926535897932384626433832795028841971693993751D;
        theValueDouble.description = "pi";

        theValueInt = new ValueDescription<>("4", ValueTypes.NUMBER);
        theValueInt.value = 3;
        theValueInt.description = "int";

        // add values to toui
        toui.add(theValueLong);
        toui.add(theValueString);
        toui.add(theValueDouble);
        toui.add(theValueInt);
    }

    public void updateVar1() {
        theValueLong.value += 1;
        toui.update(theValueLong.cloneEmpty());
    }

    public void updateVar2() {
        theValueString.value = "content: " + counter;
        toui.update(theValueString.cloneEmpty());
    }

    public void dumpCache() {
    }

    //------------------------------------------------------------
    //
    @Override
    public void update(final ValueDescription<?> _value) {
        // update from client
        System.out.println("server: update: " + _value.id + " : " + _value.value);

        // lookup value and update it

        // update cache and all clients
        toui.update(_value);

        toui.dumpCache();
    }
}
