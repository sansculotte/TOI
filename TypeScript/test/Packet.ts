import { expect } from 'chai'
import 'mocha'

import { TOICommand } from '../src/Command'
import { TOIPacket } from '../src/Packet'

describe('TOIPacket constructor', () => {
  it('requires no other argument when command is INIT', () => {
    const init = new TOIPacket(TOICommand.INIT)

    expect(init).to.be.an.instanceof(TOIPacket, 'create INIT Packet')
  })
})
