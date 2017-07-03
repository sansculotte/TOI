import * as debug from 'debug'

import { TOISocket } from './Socket'

const say = debug('toi:client')

export class TOIClient {
  static errorMessage = {
    cannotDisconnect: 'Cannot disconnect client, connection is not open.'
  }

  private socket?: TOISocket

  connect(serverURL: string) {
    this.socket = new TOISocket(serverURL)

    say('connect')
  }

  disconnect() {
    const socket = this.socket

    if (socket) {
      socket.close()
    }

    const socketIsNotOpen = socket && socket.isNotOpen()

    if (!socket || socketIsNotOpen) {
      say(TOIClient.errorMessage.cannotDisconnect)

      throw new Error(TOIClient.errorMessage.cannotDisconnect)
    }
  }
}
