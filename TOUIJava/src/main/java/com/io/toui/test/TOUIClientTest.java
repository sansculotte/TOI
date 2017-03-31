package com.io.toui.test;

import com.io.toui.model.ICommands.*;
import com.io.toui.model.*;
import com.io.toui.model.types.*;
import com.io.toui.test.serializer.JsonSerializer;
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
                    Thread.sleep(10);

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
        catch (final URISyntaxException _e) {
            _e.printStackTrace();
        }
        catch (final InterruptedException _e) {
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
                ("localhost", 8181);


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

        final Map<String, Parameter<?>> cache = toui.getValueCache();

        if (!cache.isEmpty()) {

            final Object[] objs = cache.keySet().toArray();

            final Parameter<?> parameter = cache.get(objs[0]);
            final Parameter<?> newParam  = parameter.cloneEmpty();

            if (parameter.type instanceof TypeString) {

                if (((Parameter<String>)newParam).value == null) {
                    ((Parameter<String>)newParam).value = "";
                }
                ((Parameter<String>)newParam).value += "-";
            }
            else if (parameter.type instanceof TypeNumberBase) {
                ((Parameter<Number>)newParam).value = count++;
            }
            else if (parameter.type instanceof TypeDictionary) {
//                newParam.value = new HashMap<>();

                if (((TypeDictionary)parameter.type).value instanceof TypeNumber) {

                    ((HashMap<String, Number>)newParam
                            .value).put("rnd", Math.random() * 1024);
                }


//                System.out.println(":: " + parameter.type.getClass().getName());
            }

//            parameter.dump();

            newParam.type = null;
            toui.update(newParam);

        } else {
            System.err.println("no values");
        }

    }

    //------------------------------------------------------------
    //
    @Override
    public void added(final Parameter<?> _value) {

        System.out.println("client: added: " + _value.id);
//        toui.dumpCache();
        _value.dump();
    }

    @Override
    public void updated(final Parameter<?> _value) {

        System.out.println("client: updated: " + _value.id);
//        toui.dumpCache();
        _value.dump();
    }

    @Override
    public void removed(final Parameter<?> _value) {

        System.out.println("client: removed: " + _value.id);
//        toui.dumpCache();
        _value.dump();
    }
}
