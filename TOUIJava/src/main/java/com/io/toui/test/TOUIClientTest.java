package com.io.toui.test;

import com.io.toui.model.ICommands.*;
import com.io.toui.model.*;
import com.io.toui.test.websocket.WebsocketClientTransporter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by inx on 29/11/16.
 */
public class TOUIClientTest implements Add, Remove, Update {

    //------------------------------------------------------------
    //
    public static void main(final String[] args) {

        try {
            final TOUIClientTest test = new TOUIClientTest();

            while (true) {

                try {
                    Thread.sleep(5000);

                    test.updateValue();
                }
                catch (final InterruptedException _e) {
                    break;
                }
            }
        }
        catch (final IOException _e) {
            _e.printStackTrace();
        }
        catch (URISyntaxException _e) {
            _e.printStackTrace();
        }
        catch (InterruptedException _e) {
            _e.printStackTrace();
        }

        System.out.println("finish client");
    }

    //------------------------------------------------------------
    //
    private final TOUIClient toui;

    private final Map<String, ValueDescription<?>> values = new HashMap<>();

    //------------------------------------------------------------
    //
    public TOUIClientTest() throws IOException, URISyntaxException, InterruptedException {

        // create serializer and transporter
        final JsonSerializer       serializer  = new JsonSerializer();
//        final UDPClientTransporter transporter = new UDPClientTransporter("localhost", 8888);
//        final TCPClientTransporter transporter = new TCPClientTransporter("localhost", 8888);
        final WebsocketClientTransporter transporter = new WebsocketClientTransporter
                ("localhost", 8888);


        // create toui
        toui = new TOUIClient(serializer, transporter);
        toui.setUpdateListener(this);
        toui.setAddListener(this);
        toui.setRemoveListener(this);

        // init
        toui.init();
    }

    int count;

    public void updateValue() {

        if (!values.isEmpty()) {

            Object[] objs = values.keySet().toArray();

            final ValueDescription<?> val = values.get(objs[objs.length - 1]);

            if (val.type.equals(ValueTypes.STRING)) {
                ((ValueDescription<String>)val).value += "-";
            }
            else if (val.type.equals(ValueTypes.NUMBER)) {
                ((ValueDescription<Number>)val).value = count++;
            }

            System.out.println("---- update: ");
            val.dump();
            System.out.println();

            toui.update(val.cloneEmpty());

        } else {
            System.err.println("no values");
        }

    }

    //------------------------------------------------------------
    //
    @Override
    public void update(final ValueDescription<?> _value) {

        if (values.containsKey(_value.id)) {
            System.out.println("client: update: " + _value.id + " : " + _value.description);
        }
        else {
            System.err.println("client: udpate: client does not know value with id: " + _value.id);
        }

        toui.dumpCache();
    }

    @Override
    public void add(final ValueDescription<?> _value) {

        if (!values.containsKey(_value.id)) {
            System.out.println("client: add: " + _value.id + " : " + _value.description);
            values.put(_value.id, _value);
        }
        else {
            System.err.println("client: add: already has value with id: " + _value.id);
        }

        toui.dumpCache();
    }

    @Override
    public void remove(final ValueDescription<?> _value) {

        if (values.containsKey(_value.id)) {
            System.out.println("client: remove: " + _value.id + " : " + _value.description);
            values.remove(_value.id);
        }
        else {
            System.err.println("client: remove: does not know value with id: " + _value.id);
        }
    }
}
