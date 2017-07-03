export class Packet {
  static TERMINATOR = 0
  static ID = 16
  static TIMESTAMP = 17
  static DATA = 18

  readonly command: number

  constructor(command: number) {
    this.command = command
  }

  bytes() {
    let result: number[] = []

    // TODO push packet id
    // TODO push packet timestamp
    // TODO push packet data

    result.push(Packet.TERMINATOR)

    return result
  }
}
