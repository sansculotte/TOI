export class Packet {
  static public TERMINATOR = 0
  static public ID = 16
  static public TIMESTAMP = 17
  static public DATA = 18

  constructor(command: number) {
    this.command = command
  }

  public bytes() {
    let result: number[] = []

    // TODO push packet id
    // TODO push packet timestamp
    // TODO push packet data

    result.push(Packet.TERMINATOR)

    return result
  }
}
