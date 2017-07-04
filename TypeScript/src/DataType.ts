export enum TOIDataType {
  BOOL = 16,
  INT8 = 17,
  UINT8 = 18,
  INT16 = 19,
  UINT16 = 20,
  INT32 = 21,
  UINT32 = 22,
  INT64 = 23,
  UINT64 = 24,
  FLOAT32 = 25,
  FLOAT64 = 26,
  VECTOR2F32 = 31,
  VECTOR3F32 = 37,
  VECTOR4F32 = 43,
  TSTR = 45,
  SSTR = 46,
  LSTR = 47,
  RGB8 = 48,
  RGBA8 = 49,
  ARGB8 = 50,
  ENUM = 54,
  ARRAY = 55,
  IMAGE = 57,
  BANG = 58,
  TIME = 59
}

function isNumber(n: any) {
  return typeof n === 'number'
}

function isInRange(n: number, bits: number) {
  return ((n >= 0 - Math.pow(2, bits - 1)) && (n <= Math.pow(2, bits - 1) - 1))
}

function isInUnsignedRange(n: number, bits: number) {
  return ((n >= 0) && (n <= Math.pow(2, bits) - 1))
}

export const validate = {
  BOOL: (b: any) => typeof b === 'boolean',
  // TODO improve and complete validators
  INT8: (n: any) => {
    return isNumber(n) && isInRange(n, 8)
  },
  UINT8: (n: any) => {
    return isNumber(n) && isInUnsignedRange(n, 8)
  }
}
