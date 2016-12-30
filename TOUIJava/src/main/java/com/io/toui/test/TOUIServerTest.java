package com.io.toui.test;

import com.io.toui.model.ICommands.Update;
import com.io.toui.model.*;
import com.io.toui.test.tcp.TCPServerTransporter;
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
                    Thread.sleep(3000);

                    test.updateVar1();

                    Thread.sleep(2000);

                    test.updateVar2();
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

    private final ValueDescription<Long> theValue;

    private final ValueDescription<String> theValue1;

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
        theValue = new ValueDescription<>("1", ValueTypes.NUMBER);
        theValue.value = 123L;
        theValue.description = "test description";

        theValue1 = new ValueDescription<>("2", ValueTypes.STRING);
        theValue1.value = "test content";
        theValue1.description = "test description 2";

        // add values to toui
        toui.add(theValue);
        toui.add(theValue1);
    }

    public void updateVar1() {
        toui.update(theValue.cloneEmpty());
    }

    public void updateVar2() {
        toui.update(theValue1.cloneEmpty());
    }

    //------------------------------------------------------------
    //
    @Override
    public void update(final ValueDescription<?> _value) {
        // update from client
        System.out.println("server: update: " + _value.id + " : " + _value.description);

        // lookup value and update it

        // update all clients
        toui.update(_value);
    }
}
