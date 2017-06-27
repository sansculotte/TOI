package com.io.toi.model;

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
        void added(ToiParameter<?> _value);
    }

    interface Remove {
        void removed(ToiParameter<?> _value);
    }

    interface Update {
        void updated(ToiParameter<?> _value);
    }

    interface Init {
        void init();
    }
}
