package com.io.toui.model;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by inx on 13/06/17.
 */
public interface ToiWritable {

    void write(OutputStream _outputStream) throws IOException;

}
