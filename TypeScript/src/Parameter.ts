export class TOIParameter {
  static VALUE = 32
  static LABEL = 33
  static DESCRIPTION = 34
  static ORDER = 35
  static WIDGET = 36
  static USERDATA = 37
  static TERMINATOR = 0

  bytes() {
    let result: number[] = []

    // TODO

    result.push(TOIParameter.TERMINATOR)

    return result
  }
}
