export class Parameter {
  static public VALUE = 32
  static public LABEL = 33
  static public DESCRIPTION = 34
  static public ORDER = 35
  static public WIDGET = 36
  static public USERDATA = 37
  static public TERMINATOR = 0

  public bytes() {
    let result: number[] = []

    // TODO

    result.push(Parameter.TERMINATOR)

    return result
  }
}
