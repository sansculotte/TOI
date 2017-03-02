package com.io.toui.test;

import com.io.toui.model.ICommands.*;
import com.io.toui.model.*;
import com.io.toui.model.types.*;
import com.io.toui.test.serializer.JsonSerializer;
import com.io.toui.test.websocket.WebsocketClientTransporter;

import java.io.IOException;
import java.net.URISyntaxException;
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

    //------------------------------------------------------------
    //
    public TOUIClientTest() throws IOException, URISyntaxException, InterruptedException {

        // create serializer and transporter
        final JsonSerializer serializer = new JsonSerializer();
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

        Map<String, Parameter<?>> cache = toui.getValueCache();

        if (!cache.isEmpty()) {

            Object[] objs = cache.keySet().toArray();

            final Parameter<?> val = cache.get(objs[0]);

            if (val.type instanceof TypeString) {
                ((Parameter<String>)val).value += "-";
            }
            else if (val.type instanceof TypeNumberBase) {
                ((Parameter<Number>)val).value = count++;
            }

            val.dump();

            toui.update(val.cloneEmpty());

        } else {
            System.err.println("no values");
        }

    }

    //------------------------------------------------------------
    //
    @Override
    public void added(final Parameter<?> _value) {

        System.out.println("client: added: " + _value.id);
        toui.dumpCache();
    }

    @Override
    public void updated(final Parameter<?> _value) {

        System.out.println("client: updated: " + _value.id);
        toui.dumpCache();
    }

    @Override
    public void removed(final Parameter<?> _value) {

        System.out.println("client: removed: " + _value.id);
        toui.dumpCache();
    }
}
