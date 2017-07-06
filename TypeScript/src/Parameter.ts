import * as debug from 'debug'

import { TOITypeDefinition } from './TypeDefinition'
import {
  pushIn32ToArrayBe,
  pushTypedValue,
  writeLongString,
  writeShortString,
  writeTinyString
} from './utils'

const say = debug('toi:parameter')

export class TOIParameter {
  static VALUE = 32
  static LABEL = 33
  static DESCRIPTION = 34
  static ORDER = 35
  static WIDGET = 36
  static USERDATA = 37
  static TERMINATOR = 0

  readonly id: number
  typeDefinition: TOITypeDefinition

  // TODO give proper types
  value?: any
  label?: any
  description?: any
  order?: any
  widget?: any
  userdata?: any

  constructor(
    id: number,
    typeDefinition: TOITypeDefinition,
    value: any,
    label: any,
    description: any,
    order: any,
    widget: any,
    userdata: any
  ) {
    this.id = id
    this.typeDefinition = typeDefinition

    this.value = value
    this.label = label
    this.description = description
    this.order = order
    this.widget = widget
    this.userdata = userdata

    say(`Create TOIParameter ${id}`)
  }

  bytes() {
    let result: number[] = []

    // TODO
    pushIn32ToArrayBe(this.id, result)

    result.concat(this.typeDefinition.bytes())

    // Optionals

    const value = this.value
    const typeDefinition = this.typeDefinition
    const label = this.label
    const description = this.description
    const order = this.order
    const widget = this.widget
    const userdata = this.userdata

    if (value) {
      result.push(TOIParameter.VALUE)
      pushTypedValue(typeDefinition.typeid, value, result)
    }

    if (label) {
      result.push(TOIParameter.LABEL)
      result = writeTinyString(label, result)
    }

    if (description) {
      result.push(TOIParameter.DESCRIPTION)
      result = writeShortString(description, result)
    }

    if (order) {
      result.push(TOIParameter.ORDER)
      pushIn32ToresultBe(order, result)
    }

    if (widget) {
      say('widget ignored for now')
    }

    if (userdata) {
      result.push(Parameter.USERDATA)
      pushIn32ToresultBe(userdata.length, result)

      const userresult = [].slice.call(userdata)
      result = result.concat(userresult)
    }

    result.push(TOIParameter.TERMINATOR)

    return result
  }
}
