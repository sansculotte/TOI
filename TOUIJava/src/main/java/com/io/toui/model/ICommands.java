package com.io.toui.model;

/**
 * Created by inx on 30/11/16.
 */
public interface ICommands {

    //------------------------------------------------------------
    //
    String INIT = "init";

    String VERSION = "version";

    String ADD = "add";

    String REMOVE = "remove";

    String UPDATE = "update";

    //------------------------------------------------------------
    //
    interface Add {
        void add(ValueDescription<?> _value);
    }

    interface Remove {
        void remove(ValueDescription<?> _value);
    }

    interface Update {
        void update(ValueDescription<?> _value);
    }

    interface Init {
        void init();
    }
}
