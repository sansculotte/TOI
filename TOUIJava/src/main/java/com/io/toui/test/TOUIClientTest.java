package com.io.toui.test;

import com.io.toui.model.*;
import com.io.toui.model.ICommands.*;

import java.net.SocketException;
import java.net.UnknownHostException;
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
        catch (SocketException | UnknownHostException _e) {
            _e.printStackTrace();
        }

        System.out.println("finish client");
    }

    //------------------------------------------------------------
    //
    private final TOUI toui;

    private final Map<String, ValueDescription<?>> values = new HashMap<>();

    //------------------------------------------------------------
    //
    public TOUIClientTest() throws SocketException, UnknownHostException {

        // create serializer and transporter
        final JsonSerializer       serializer  = new JsonSerializer();
        final UDPClientTransporter transporter = new UDPClientTransporter("localhost", 8888);

        // create toui
        toui = new TOUI(serializer, transporter);
        toui.setUpdateListener(this);
        toui.setAddListener(this);
        toui.setRemoveListener(this);

        // init
        toui.init();
    }

    public void updateValue() {

        if (!values.isEmpty()) {

            final ValueDescription<?> val = values.get(values.keySet().toArray()[0]);

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
