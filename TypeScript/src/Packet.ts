import { TOICommand } from './Command'

export class TOIPacket {
  static TERMINATOR = 0
  static ID = 16
  static TIMESTAMP = 17
  static DATA = 18

  readonly command: TOICommand
  id?: number

  constructor(command: TOICommand, id?: number) {
    this.command = command

    this.id = id
  }

  bytes(): Uint8Array {
    let result: number[] = []

    // TODO push packet id
    // TODO push packet timestamp
    // TODO push packet data

    result.push(TOIPacket.TERMINATOR)

    return new Uint8Array(result)
  }
}
