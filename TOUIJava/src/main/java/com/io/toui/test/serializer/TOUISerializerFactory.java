package com.io.toui.test.serializer;

import com.io.toui.model.ITOUISerializer;

/**
 * Created by inx on 08/04/17.
 */
public class TOUISerializerFactory {

    // TODO: do a proper factory
    public synchronized ITOUISerializer createSerializer() {
        return new JsonSerializer();
    }

}
