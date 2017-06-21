// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

package com.io.toui.model;

import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class ToiTypes extends KaitaiStruct {

    public static ToiTypes fromFile(String fileName) throws IOException {

        return new ToiTypes(new KaitaiStream(fileName));
    }

    public enum TouiPacket {
        PACKET_ID(17),
        PACKET_TIME(18),
        DATA(19);

        private final long theid;

        TouiPacket(long id) {

            this.theid = id;
        }

        public long id() {

            return theid;
        }

        private static final Map<Long, TouiPacket> byId = new HashMap<>(3);

        static {
            for (TouiPacket e : TouiPacket.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiPacket byId(long id) {

            return byId.get(id);
        }
    }

    public enum TouiParameter {
        VALUE(34),
        LABEL(35),
        DESCRIPTION(36),
        ORDER(37),
        WIDGET(38),
        USERDATA(39);

        private final long id;

        TouiParameter(long id) {

            this.id = id;
        }

        public long id() {

            return id;
        }

        private static final Map<Long, TouiParameter> byId = new HashMap<Long, TouiParameter>(6);

        static {
            for (TouiParameter e : TouiParameter.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiParameter byId(long id) {

            return byId.get(id);
        }
    }

    public enum TouiTypedef {
        SUBTYPE(33),
        DEFAULTVALUE(49),
        MIN(51),
        MAX(52),
        MULT(53),
        SCALE(54),
        UNIT(55),
        ENTRIES(83);

        private final long id;

        TouiTypedef(long id) {

            this.id = id;
        }

        public long id() {

            return id;
        }

        private static final Map<Long, TouiTypedef> byId = new HashMap<Long, TouiTypedef>(8);

        static {
            for (TouiTypedef e : TouiTypedef.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiTypedef byId(long id) {

            return byId.get(id);
        }
    }

    public enum TouiDatatypes {
        BOOL(33),
        INT8(34),
        UINT8(35),
        INT16(36),
        UINT16(37),
        INT32(38),
        UINT32(39),
        INT64(40),
        UINT64(41),
        FLOAT32(42),
        FLOAT64(43),
        TSTR(45),
        SSTR(46),
        LSTR(47);

        private final long id;

        TouiDatatypes(long id) {

            this.id = id;
        }

        public long id() {

            return id;
        }

        private static final Map<Long, TouiDatatypes> byId = new HashMap<Long, TouiDatatypes>(14);

        static {
            for (TouiDatatypes e : TouiDatatypes.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiDatatypes byId(long id) {

            return byId.get(id);
        }
    }

    public enum TouiCommands {
        VERSION(0),
        INIT(1),
        ADD(2),
        UPDATE(3),
        REMOVE(4);

        private final long id;

        TouiCommands(long id) {

            this.id = id;
        }

        public long id() {

            return id;
        }

        private static final Map<Long, TouiCommands> byId = new HashMap<Long, TouiCommands>(5);

        static {
            for (TouiCommands e : TouiCommands.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiCommands byId(long id) {

            return byId.get(id);
        }
    }

    public enum TouiScale {
        LIN(0),
        LOG(1);

        private final long id;

        TouiScale(long id) {

            this.id = id;
        }

        public long id() {

            return id;
        }

        private static final Map<Long, TouiScale> byId = new HashMap<Long, TouiScale>(2);

        static {
            for (TouiScale e : TouiScale.values()) {
                byId.put(e.id(), e);
            }
        }

        public static TouiScale byId(long id) {

            return byId.get(id);
        }
    }

    public ToiTypes(KaitaiStream _io) {

        super(_io);
        this._root = this;
        _read();
    }

    public ToiTypes(KaitaiStream _io, KaitaiStruct _parent) {

        super(_io);
        this._parent = _parent;
        this._root = this;
        _read();
    }

    public ToiTypes(KaitaiStream _io, KaitaiStruct _parent, ToiTypes _root) {

        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {

    }

    public static class TinyString extends KaitaiStruct {

        public static TinyString fromFile(String fileName) throws IOException {

            return new TinyString(new KaitaiStream(fileName));
        }

        public TinyString(KaitaiStream _io) {

            super(_io);
            _read();
        }

        public TinyString(KaitaiStream _io, KaitaiStruct _parent) {

            super(_io);
            this._parent = _parent;
            _read();
        }

        public TinyString(KaitaiStream _io, KaitaiStruct _parent, ToiTypes _root) {

            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {

            this.myLen = this._io.readU1();
            this.data = new String(this._io.readBytes(myLen()), Charset.forName("UTF-8"));
        }

        private int          myLen;

        private String       data;

        private ToiTypes     _root;

        private KaitaiStruct _parent;

        public int myLen() {

            return myLen;
        }

        public String data() {

            return data;
        }

        public ToiTypes _root() {

            return _root;
        }

        public KaitaiStruct _parent() {

            return _parent;
        }
    }

    public static class ShortString extends KaitaiStruct {

        public static ShortString fromFile(String fileName) throws IOException {

            return new ShortString(new KaitaiStream(fileName));
        }

        public ShortString(KaitaiStream _io) {

            super(_io);
            _read();
        }

        public ShortString(KaitaiStream _io, KaitaiStruct _parent) {

            super(_io);
            this._parent = _parent;
            _read();
        }

        public ShortString(KaitaiStream _io, KaitaiStruct _parent, ToiTypes _root) {

            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {

            this.myLen = this._io.readU2be();
            this.data = new String(this._io.readBytes(myLen()), Charset.forName("UTF-8"));
        }

        private int          myLen;

        private String       data;

        private ToiTypes     _root;

        private KaitaiStruct _parent;

        public int myLen() {

            return myLen;
        }

        public String data() {

            return data;
        }

        public ToiTypes _root() {

            return _root;
        }

        public KaitaiStruct _parent() {

            return _parent;
        }
    }

    public static class LongString extends KaitaiStruct {

        public static LongString fromFile(String fileName) throws IOException {

            return new LongString(new KaitaiStream(fileName));
        }

        public LongString(KaitaiStream _io) {

            super(_io);
            _read();
        }

        public LongString(KaitaiStream _io, KaitaiStruct _parent) {

            super(_io);
            this._parent = _parent;
            _read();
        }

        public LongString(KaitaiStream _io, KaitaiStruct _parent, ToiTypes _root) {

            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {

            this.myLen = this._io.readU4be();
            this.data = new String(this._io.readBytes(myLen()), Charset.forName("UTF-8"));
        }

        private long         myLen;

        private String       data;

        private ToiTypes     _root;

        private KaitaiStruct _parent;

        public long myLen() {

            return myLen;
        }

        public String data() {

            return data;
        }

        public ToiTypes _root() {

            return _root;
        }

        public KaitaiStruct _parent() {

            return _parent;
        }
    }

    public static class Userdata extends KaitaiStruct {

        public static Userdata fromFile(String fileName) throws IOException {

            return new Userdata(new KaitaiStream(fileName));
        }

        public Userdata(KaitaiStream _io) {

            super(_io);
            _read();
        }

        public Userdata(KaitaiStream _io, KaitaiStruct _parent) {

            super(_io);
            this._parent = _parent;
            _read();
        }

        public Userdata(KaitaiStream _io, KaitaiStruct _parent, ToiTypes _root) {

            super(_io);
            this._parent = _parent;
            this._root = _root;
            _read();
        }

        private void _read() {

            this.myLen = this._io.readU4be();
            this.data = this._io.readBytes(myLen());
        }

        private long         myLen;

        private byte[]       data;

        private ToiTypes     _root;

        private KaitaiStruct _parent;

        public long myLen() {

            return myLen;
        }

        public byte[] data() {

            return data;
        }

        public ToiTypes _root() {

            return _root;
        }

        public KaitaiStruct _parent() {

            return _parent;
        }
    }

    private ToiTypes     _root;

    private KaitaiStruct _parent;

    public ToiTypes _root() {

        return _root;
    }

    public KaitaiStruct _parent() {

        return _parent;
    }
}
