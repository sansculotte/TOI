"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var TOIPacket = (function () {
    function TOIPacket(command) {
        this.command = command;
    }
    TOIPacket.prototype.bytes = function () {
        var result = [];
        // TODO push packet id
        // TODO push packet timestamp
        // TODO push packet data
        result.push(TOIPacket.TERMINATOR);
        return new Uint8Array(result);
    };
    TOIPacket.TERMINATOR = 0;
    TOIPacket.ID = 16;
    TOIPacket.TIMESTAMP = 17;
    TOIPacket.DATA = 18;
    return TOIPacket;
}());
exports.TOIPacket = TOIPacket;
