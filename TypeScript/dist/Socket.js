"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var debug = require("debug");
var say = debug('toi:transport');
var ReadyStateEnum;
(function (ReadyStateEnum) {
    ReadyStateEnum[ReadyStateEnum["CONNECTING"] = 0] = "CONNECTING";
    ReadyStateEnum[ReadyStateEnum["OPEN"] = 1] = "OPEN";
    ReadyStateEnum[ReadyStateEnum["CLOSING"] = 2] = "CLOSING";
    ReadyStateEnum[ReadyStateEnum["CLOSED"] = 3] = "CLOSED";
})(ReadyStateEnum || (ReadyStateEnum = {}));
var TOISocket = (function () {
    function TOISocket(serverURL) {
        // Something like ws://www.example.com/socketserver
        this.serverURL = serverURL;
    }
    TOISocket.prototype.close = function () {
        if (this.websocket) {
            this.websocket.close();
        }
        debug('close');
    };
    TOISocket.prototype.connect = function () {
        var _this = this;
        var websocket = new WebSocket(this.serverURL);
        websocket.binaryType = 'arraybuffer';
        websocket.onmessage = function (event) { return _this.receive(event.data); };
        this.websocket = websocket;
        say('connect');
    };
    TOISocket.prototype.isNotOpen = function () {
        return this.websocket.readyState !== TOISocket.readyState.OPEN;
    };
    TOISocket.prototype.receive = function (packet) {
        // TODO decode event.data
        say('receive');
    };
    TOISocket.prototype.send = function (packet) {
        var websocket = this.websocket;
        var readyState = websocket.readyState;
        switch (readyState) {
            case TOISocket.readyState.CONNECTING:
            case TOISocket.readyState.CLOSED:
            case TOISocket.readyState.CLOSING:
                say(TOISocket.errorMessage.cannotSend);
                throw new Error(TOISocket.errorMessage.cannotSend);
            case TOISocket.readyState.OPEN:
                this.websocket.send(packet);
                say('send');
                break;
        }
    };
    TOISocket.errorMessage = {
        cannotSend: 'Cannot send data, websocket is not open.'
    };
    TOISocket.readyState = ReadyStateEnum;
    return TOISocket;
}());
exports.TOISocket = TOISocket;
