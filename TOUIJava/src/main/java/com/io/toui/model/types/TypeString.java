package com.io.toui.model.types;

/**
 * Created by inx on 02/03/17.
 */
public class TypeString extends TypeDefinition<String> {

    public enum Format {
        Text, Multiline, Password, File, Directory, URL, IP
    }

    public Format format = Format.Text;

    public String filemask;

    public long maxchars;

    public TypeString() {

        super(STRING);

        setDefaultValue("");
    }
}
