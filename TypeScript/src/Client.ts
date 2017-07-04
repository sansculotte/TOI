import * as debug from 'debug'

import { TOICommand } from './Command'
import { TOIPacket } from './Packet'
import { TOISocket } from './Socket'

const say = debug('toi:client')

export class TOIClient {
  private socket?: TOISocket

  constructor(serverURL: string) {
    this.socket = new TOISocket(serverURL)
  }

  sendUpdate() {
    const socket = this.socket

    try {
      const packet = new TOIPacket(TOICommand.UPDATE)

      // TODO add data to packet.

      if (socket) {
        socket.send(packet)
        say('send update')
      }
    } catch (error) {
      throw error
    }
  }
}
