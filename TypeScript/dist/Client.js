"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var debug = require("debug");
var Socket_1 = require("./Socket");
var say = debug('toi:client');
var TOIClient = (function () {
    function TOIClient() {
    }
    TOIClient.prototype.connect = function (serverURL) {
        this.socket = new Socket_1.TOISocket(serverURL);
        say('connect');
    };
    TOIClient.prototype.disconnect = function () {
        var socket = this.socket;
        if (socket) {
            socket.close();
        }
        var socketIsNotOpen = socket && socket.isNotOpen();
        if (!socket || socketIsNotOpen) {
            say(TOIClient.errorMessage.cannotDisconnect);
            throw new Error(TOIClient.errorMessage.cannotDisconnect);
        }
    };
    TOIClient.errorMessage = {
        cannotDisconnect: 'Cannot disconnect client, connection is not open.'
    };
    return TOIClient;
}());
exports.TOIClient = TOIClient;
