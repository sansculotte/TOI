"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var debug = require("debug");
var Command_1 = require("./Command");
var Packet_1 = require("./Packet");
var Socket_1 = require("./Socket");
var say = debug('toi:client');
var TOIClient = (function () {
    function TOIClient(serverURL) {
        this.socket = new Socket_1.TOISocket(serverURL);
    }
    TOIClient.prototype.sendUpdate = function () {
        var socket = this.socket;
        try {
            var packet = new Packet_1.TOIPacket(Command_1.TOICommand.UPDATE);
            // TODO add data to packet.
            if (socket) {
                socket.send(packet);
                say('send update');
            }
        }
        catch (error) {
            throw error;
        }
    };
    return TOIClient;
}());
exports.TOIClient = TOIClient;
