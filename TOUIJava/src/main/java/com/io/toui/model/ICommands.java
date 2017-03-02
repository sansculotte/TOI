package com.io.toui.model;

/**
 * Created by inx on 30/11/16.
 */
public interface ICommands {

    //------------------------------------------------------------
    //
    String INIT = "init";

    String VERSION = "version";

    String ADD = "added";

    String REMOVE = "removed";

    String UPDATE = "updated";

    //------------------------------------------------------------
    //
    interface Add {
        void added(ValueDescription<?> _value);
    }

    interface Remove {
        void removed(ValueDescription<?> _value);
    }

    interface Update {
        void updated(ValueDescription<?> _value);
    }

    interface Init {
        void init();
    }
}
