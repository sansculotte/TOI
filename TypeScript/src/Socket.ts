import * as debug from 'debug'

import { TOIPacket } from './Packet'
import { TOITransport } from './Transport'

const say = debug('toi:transport')

enum ReadyStateEnum {
  CONNECTING = 0,
  OPEN = 1,
  CLOSING = 2,
  CLOSED = 3
}

export class TOISocket implements TOITransport {
  static errorMessage = {
    notConnected: 'Connection is not open.'
  }

  static readyState = ReadyStateEnum

  private readonly serverURL: string
  private websocket?: any

  constructor(serverURL: string) {
    // Something like ws://www.example.com/socketserver
    this.serverURL = serverURL
  }

  close() {
    if (this.websocket) {
      this.websocket.close()
    }

    debug('close')
  }

  connect() {
    let websocket = new WebSocket(this.serverURL)

    websocket.binaryType = 'arraybuffer'

    websocket.onmessage = (event) => this.receive(event.data)

    this.websocket = websocket
    say('connect')
  }

  isOpen() {
    const websocket = this.websocket

    return websocket && websocket.readyState === TOISocket.readyState.OPEN
  }

  receive(packet: TOIPacket) {
    // TODO decode event.data
    say('receive')
  }

  send(packet: TOIPacket) {
    const websocket = this.websocket
    const readyState = websocket.readyState

    switch (readyState) {
      case TOISocket.readyState.CONNECTING:
      case TOISocket.readyState.CLOSED:
      case TOISocket.readyState.CLOSING:
        say(TOISocket.errorMessage.notConnected)

        throw new Error(TOISocket.errorMessage.notConnected)

      case TOISocket.readyState.OPEN:
        this.websocket.send(packet.bytes())
        say('send')
        break
    }
  }
}
