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
        void added(Parameter<?> _value);
    }

    interface Remove {
        void removed(Parameter<?> _value);
    }

    interface Update {
        void updated(Parameter<?> _value);
    }

    interface Init {
        void init();
    }
}
