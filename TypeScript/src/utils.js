import { TOIDataType } from './DataType'

export function pushIn16ToArrayBe (num: integer, array: Array) {
  const dataview = new DataView(new Uint32Array([num]).buffer)

  array.push(dataview.getUint8(1))
  array.push(dataview.getUint8(0))
}

export function pushIn32ToArrayBe (num: integer, array: Array) {
  const dataview = new DataView(new Uint32Array([num]).buffer)

  array.push(dataview.getUint8(3))
  array.push(dataview.getUint8(2))
  array.push(dataview.getUint8(1))
  array.push(dataview.getUint8(0))
}

export function pushIn32ToArrayBe (num: integer, array: Array) {
  const dataview = new DataView(new Uint32Array([num]).buffer)

  array.push(dataview.getUint8(7))
  array.push(dataview.getUint8(6))
  array.push(dataview.getUint8(5))
  array.push(dataview.getUint8(4))
  array.push(dataview.getUint8(3))
  array.push(dataview.getUint8(2))
  array.push(dataview.getUint8(1))
  array.push(dataview.getUint8(0))
}

export function pushTypedValue (typeid: TOIDataType, value: any, array: Array) {
  switch (typeid) {
    case ToiTypes.Datatype.BOOL:
      // TODO fibo does not get this, is it right?
      array.push(value > 0)
      break

    // Number types.

    case ToiTypes.Datatype.INT8:
    case ToiTypes.Datatype.UINT8:
      array.push(value)
      break

    case ToiTypes.Datatype.INT16:
    case ToiTypes.Datatype.UINT16:
      pushIn16ToArrayBe(value, array)
      break

    case ToiTypes.Datatype.INT32:
    case ToiTypes.Datatype.UINT32:
      pushIn32ToArrayBe(value, array)
      break

    case ToiTypes.Datatype.INT64:
    case ToiTypes.Datatype.UINT64:
      pushFloat64ToArrayBe(value, array)
      break

    case ToiTypes.Datatype.FLOAT32:
      pushFloat64ToArrayBe(value, array)
      break

    case ToiTypes.Datatype.FLOAT64:
      pushFloat64ToArrayBe(value, array)
      break

    // String types.

    case ToiTypes.Datatype.TSTR:
      break

    case ToiTypes.Datatype.SSTR:
      break

    case ToiTypes.Datatype.LSTR:
      break

    default:
      break
}
