meta:
  id: toi_types
  file-extension: toi
  endian: be

enums:
  packet:
    0x00: terminator
    0x10: id
    0x11: timestamp
    0x12: data

  command:
    0x01: version
    0x02: init
    0x03: add
    0x04: update
    0x05: remove
    0x06: updateValue

  metadata:
    0x1a: version
    0x1b: capabilities
    0x1c: commands

  parameter:
    0x20: value
    0x21: label
    0x22: description
    0x23: order
    0x24: parent
    0x25: widget
    0x26: userdata

  type_definition:
    0x30: defaultvalue

  type_number:
    0x30: defaultvalue
    0x31: min
    0x32: max
    0x33: mult
    0x34: scale
    0x35: unit

  type_vector:
    0x30: defaultvalue
    0x31: min
    0x32: max
    0x33: mult
    0x34: scale
    0x35: unit

  type_enum:
    0x30: defaultvalue
    0x31: entries

  type_fixed_array:
    0x30: defaultvalue

  type_dynamic_array:
    0x30: defaultvalue

  type_compound:
    0x30: defaultvalue

  datatype:
    0x10: bool
    0x11: int8
    0x12: uint8
    0x13: int16
    0x14: uint16
    0x15: int32
    0x16: uint32
    0x17: int64
    0x18: uint64
    0x19: float32
    0x1a: float64
    0x1f: vector2f32
    0x25: vector3f32
    0x2b: vector4f32
    0x2d: tstring # tiny string
    0x2e: sstring # short string
    0x2f: string # long string
    0x30: rgb
    0x31: rgba
    0x32: enum
    0x33: fixedArray
    0x34: dynamicArray
    0x36: image
    0x37: bang
    0x38: time
    0x39: group # parameter group
    0x3a: compound

  number_scale:
    0x00: lin
    0x01: log
    0x02: exp2

  widget:
    0x50: type
    0x51: enabled
    0x52: visible
    0x53: label_visible
    0x54: value_visible
    0x55: labe_position

  label_position:
    0x00: left
    0x01: right
    0x02: top
    0x03: bottom
    0x04: center

  widget_type:
    0x10: textbox
    0x11: numberbox
    0x12: button
    0x13: checkbox
    0x14: radiobutton
    0x15: slider
    0x16: dial
    0x17: colorbox
    0x18: table
    0x19: treeview
    0x1a: dropdown
    0x1f: xyfield


types:
  tiny_string:
    seq:
      - id: my_len
        type: u1
      - id: data
        type: str
        size: my_len
        encoding: UTF-8
  short_string:
    seq:
      - id: my_len
        type: u2
      - id: data
        type: str
        size: my_len
        encoding: UTF-8
  long_string:
    seq:
      - id: my_len
        type: u4
      - id: data
        type: str
        size: my_len
        encoding: UTF-8
  userdata:
    seq:
      - id: my_len
        type: u4
      - id: data
        size: my_len
