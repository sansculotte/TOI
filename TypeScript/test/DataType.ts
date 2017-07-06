import { expect } from 'chai'
import 'mocha'

import { TOIDataType, validate } from '../src/DataType'

describe('TOIDataType', () => {
  it('can be imported', () => {
    expect(TOIDataType.BOOL).to.equal(16)
  })
})

describe('validate', () => {
  // TODO add all validate functions.
  it('can check BOOL', () => {
    expect(validate.BOOL(true), 'true').to.be.true
    expect(validate.BOOL(false), 'false').to.be.true
    expect(validate.BOOL('not a boolean'), 'a string').to.be.false
  })

  it('can check INT8', () => {
    expect(validate.INT8(1), 'positive number').to.be.true
    expect(validate.INT8(-23), 'negative number').to.be.true
    expect(validate.INT8('not an integer'), 'a string').to.be.false
    expect(validate.INT8(10000000), 'out of bound number').to.be.false
  })

  it('can check UINT8', () => {
    expect(validate.UINT8(100), 'positive number').to.be.true
    expect(validate.UINT8(-1), 'negative number').to.be.false
  })
})
