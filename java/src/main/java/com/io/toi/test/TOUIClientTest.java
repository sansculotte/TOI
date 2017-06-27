package com.io.toi.test;

import com.io.toi.model.ICommands.*;
import com.io.toi.model.*;
import com.io.toi.model.types.ToiTypeNumber;
import com.io.toi.model.types.ToiTypeSTRING;
import com.io.toi.test.websocket.client.WebsocketClientTransporter;

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
                    Thread.sleep(1000);

//                    test.updateValue();
                }
                catch (final InterruptedException _e) {
                    break;
                }
            }
        }
        catch (final IOException | InterruptedException | URISyntaxException _e) {
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
//        final UDPClientTransporter transporter = new UDPClientTransporter("localhost", 8888);
//        final TCPClientTransporter transporter = new TCPClientTransporter("localhost", 8888);
        final WebsocketClientTransporter transporter = new WebsocketClientTransporter
                ("localhost", 8181);


        // create toi
        toui = new TOUIClient(transporter);
        toui.setUpdateListener(this);
        toui.setAddListener(this);
        toui.setRemoveListener(this);

        // init
        toui.init();
    }

    int count;

    public void updateValue() {

        final Map<Integer, ToiParameter<?>> cache = toui.getValueCache();

        if (!cache.isEmpty()) {

            final Object[] objs = cache.keySet().toArray();

            final ToiParameter<?> parameter = cache.get(objs[0]);

            final ToiParameter<?> newParam  = parameter.cloneEmpty();

            if (parameter.getType() instanceof ToiTypeSTRING) {

                final ToiParameter<String> stringParam = (ToiParameter<String>)newParam;

                if (stringParam.getValue() == null) {
                    stringParam.setValue("");
                }
                stringParam.setValue(stringParam.getValue() + "-");
            }
            else if (parameter.getType() instanceof ToiTypeNumber) {
                ((ToiParameter<Number>)newParam).setValue(count++);
            }
//            else if (parameter.type instanceof TypeDictionary) {
////                newParam.value = new HashMap<>();
//
//                if (((TypeDictionary)parameter.type).value instanceof TypeNumber) {
//
//                    ((HashMap<String, Number>)newParam
//                            .value).put("rnd", Math.random() * 1024);
//                }
//
//
////                System.out.println(":: " + parameter.type.getClass().getName());
//            }

//            parameter.dump();

            toui.update(newParam);

        } else {
            System.err.println("no values");
        }

    }

    //------------------------------------------------------------
    //
    @Override
    public void added(final ToiParameter<?> _value) {

        System.out.println("client: added: " + _value.getId());
//        toi.dumpCache();
        _value.dump();
    }

    @Override
    public void updated(final ToiParameter<?> _value) {

        System.out.println("client: updated: " + _value.getId());
//        toi.dumpCache();
        _value.dump();
    }

    @Override
    public void removed(final ToiParameter<?> _value) {

        System.out.println("client: removed: " + _value.getId());
//        toi.dumpCache();
        _value.dump();
    }
}
