import * as debug from 'debug'

import { TOIDataType, validate } from './DataType'
import { TOITypeNumber } from './TypeNumber'

const say = debug('toi:transport')

export class TOITypeDefinition {
  static DEFAULT_VALUE = 48
  static TERMINATOR = 0

  static errorMessage = {
    invalidDefaultValue: 'Invalid defaultValue provided.'
  }

  typeid: TOIDataType
  min?: number
  max?: number
  multipleof?: number
  scale?: number
  unit?: number

  constructor(
    typeid: TOIDataType,
    defaultValue?: any,
    min?: number,
    max?: number,
    multipleof?: number,
    scale?: number,
    unit?: number
  ) {
    this.typeid = typeid

    // TODO should min, max, scale, etc. be validated as their type?
    this.min = min
    this.max = max
    this.multipleof = multipleof
    this.scale = scale
    this.unit = unit

    let defaultValueIsValid = false

    if (defaultValue) {
      switch (typeid) {
        case TOIDataType.BOOL:
          defaultValueIsValid = validate.BOOL(defaultValue)
          break

        case TOIDataType.INT8:
          defaultValueIsValid = validate.INT8(defaultValue)
          break

        case TOIDataType.UINT8:
          defaultValueIsValid = validate.UINT8(defaultValue)
          break

        // TODO add more type checks

        default:
          break
      }

      if (defaultValueIsValid) {
        this.defaultValue = defaultValue
      } else {
        say(TOITypeDefinition.errorMessage.invalidDefaultValue)

        throw new Error(TOITypeDefinition.errorMessage.invalidDefaultValue)
      }
    }
  }

  bytes(): Uint8Array {
    let result: number[] = []

    const defaultValue = this.defaultValue
    const typeid = this.typeid

    result.push(typeid)

    switch (typeid) {
      case TOIDataType.BOOL:
        // Only default in BOOL.
        if (this.defaultValue) {
          result.push(TOITypeDefinition.DEFAULT_VALUE)
          result.push(defaultValue)
        }

        break

      case TOIDataType.INT8:
      case TOIDataType.UINT8:
        if (this.defaultValue) {
          result.push(TOITypeDefinition.DEFAULT_VALUE)
          pushTypedValue(typeid, defaultValue, result)
        }

        if (this.min) {
          result.push(TOITypeNumber.MIN)
          pushTypedValue(typeid, this.min, result)
        }

        if (this.max) {
          result.push(TOITypeNumber.MAX)
          pushTypedValue(typeid, this.max, result)
        }

        if (this.multipleof) {
          result.push(TOITypeNumber.MULT)
          pushTypedValue(typeid, this.multipleof, result)
        }

        if (this.scale) {
          result.push(TOITypeNumber.SCALE)
          pushTypedValue(typeid, this.scale, result)
        }

        if (this.unit) {
          result.push(TOITypeNumber.UNIT)
          pushTypedValue(typeid, this.unit, result)
        }

        break

      // TODO add more types

      default:
        break
    }
    result.push(TOITypeDefinition.TERMINATOR)

    return new Uint8Array(result)
  }
}
