import { TOIPacket } from './Packet'

export interface TOITransport {
  connect(): void,
  close(): void,
  receive(packet: TOIPacket): void,
  send(packet: TOIPacket): void
}
