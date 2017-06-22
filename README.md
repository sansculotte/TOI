# TOI
Totally Optional Interface

A binary data-format definition to describe data values and user interface elements.
It is intended to expose parameters (values) from a host application to a client in a defined way. It was created with UI clients in mind which update values at the host application. It can also be used in a non-UI case.


## TOI Levels:
- __Value__: Number, String, Color, … This is the value without visual representation
- __Widget__ (optional): Button, Slider, … This is the visual representation of a Value. A Widget must be implemented client-side. The protocol defines standard widgets for basic types. Optionally complex types can be added when needed.
- __Layout__ (optional): Placement of widgets on a screen: The Layouting of Widgets defines how widgets are placed on screen by defining standard containers.
- __Style__ (optional): Look (colors, shading, ...) of widgets on a screen: CSS styling of widgets

The first draft version of TOI only defines 1. (Values) and 2. (Widgets)

##Contributors
eno, ingo, ingolf, joreg, karsten

## Status

draft...

## Endianess

The format is using big endian

## Framing

TODO

## Types

- tiny-string: prefixed with size [1-byte] followed by [UTF-8 string-data]
- short-string: prefixed with size [2-byte] followed by [UTF-8 string-data]
- long-string: prefixed with size [4-byte] followed by [UTF-8 string-data]

## Package

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| **command** | - | 1 byte | - | n | command of package |
| id | 0x10(16) | 4 byte | 0 | y | optional packet id |
| timestamp | 0x11(17) | 8 byte | 0 | y | optional timestamp |
| data | 0x12(18) | - | - | y | package data. type depends on command |
| **terminator** | 0 | 1 byte | 0 | n | package terminator |

note: we may want to send id/timestamp before the data, to decide if packet is valid (udp case), prefix the value with data-id. otherwise we need to parse data to get to id/timestamp

### command table:

| command   | ID   | expected data | comment   |
|-----------|------|---------------|-----------|
| version | 0x01 | Meta Data |
| init | 0x02 | - / Paramter | if no data is sent: request for all parameters, if a paramter is sent: request for one parameter
| add | 0x03 | Parameter |
| update | 0x04 |	Parameter
| remove | 0x05 | Parameter
| updateValue? | 0x06 | specialized smallest update value format
| set layout? | 0x07 | Layout data
| set style? | 0x08 | Style data


- data provider ususally send: version, add, update, remove
- data clients usually send: init, update

## Meta Data (0x13)

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| **version** | 0x1a	(26) | tiny-string | "" | n | semver
| **capabilities** | 0x1b (27) | 1-byte | 1 | n | capbility of TOI
| **commands** | 0x1c (28) | 1-byte | 0 | n | (max 8 commands enough?) |
| **terminator** | 0 | 1 byte | 0 | n | terminator

### capabilities (0x1b) (1 byte)

| Bit 7   | Bit 6   | Bit 5   | Bit 4   | Bit 3   | Bit 2   | Bit 1   | Bit 0   |
|---------|---------|---------|---------|---------|---------|---------|---------|
|-|-|-|-| Style | Layout | Widget | Value |

### commands (0x1c) (1 byte)

| Bit 7   | Bit 6   | Bit 5   | Bit 4   | Bit 3   | Bit 2   | Bit 1   | Bit 0   |
|---------|---------|---------|---------|---------|---------|---------|---------|
|-|-| updateValue | remove | update | add | init | version |


## Parameter (0x13):

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| **id** | - | 4 byte | 0 | n | unique identifier
| **type** |	- | TypeDefinition | - | n | typedefinition of value
| value | 0x20 (32) | known from typedefinition | ? | y |	value (length is known by type!)
| label | 0x21 (33)	| tiny-string | "" | y | Human readable identifier
| desc | 0x22 (34) | short-string | "" | y | can be shown as a tooltip
| order | 0x23 (35)	|	int32 | 0 | y | allows for most simple layout
| widget | 0x24 (36) | widget data | text-input-widget | y | if not specified a default widget is used
| userdata | 0x25 (37) | size of value (32bit) followed by userdata | - | y | various user-data. e.g.: metadata, tags, ...
| terminator | 0 | 1 byte | 0 | n | terminator


## Typedefinition:


| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| **type-id** | - |  1-byte (see type-table) | 0x2f | n | type of value
| default | 0x30 (48) | defined by type-ud | depending on type | y | default data
| ... type options... | | ||||
| **terminator** | 0 | 1 byte | 0 | n | terminator


### type-table: (1byte)

| typename   | hex (dec)   |
| -----------|-------------|
| boolean | 0x10 (16) |
| int8 | 0x11 (17) |
| uint8 | 0x12 (18) |
| int16 | 0x13	(19) |
| uint16 | 0x14 (20) |
| int32 | 0x15	(21) |
| uint32 | 0x16	(22) |
| int64 | 0x17 (23) |
| uint64 | 0x18	(24) |
| float32 | 0x19 (25) |
| float64 | 0x1a (26) |
| Vector2i8 | 0x1b |
| Vector2i16 | 0x1c |
| Vector2i32 | 0x1d |
| Vector2i64 | 0x1e |
| Vector2f32 | 0x1f |
| Vector2f64 | 0x20 |
| Vector3i8 | 0x21 |
| Vector3i16 | 0x22 |
| Vector3i32 | 0x23 |
| Vector3i64 | 0x24 |
| Vector3f32 | 0x25 |
| Vector3f64 | 0x26 |
| Vector4i8 | 0x27 |
| Vector4i16 | 0x28 |
| Vector4i32 | 0x29 |
| Vector4i64 | 0x2a |
| Vector4f32 | 0x2b |
| Vector4f64 | 0x2c |
| String (tiny) | 0x2d |
| String (short) | 0x2e |
| String (long) | 0x2f |
| RGB8 | 0x30 (48) |
| RGBA8 | 0x31 (49) |
| ARGB8 | 0x32 (50) |
| BGR8 | 0x33 (51) |
| BGRA8 | 0x34 (52) |
| ABGR8 | 0x35 (53) |
| Enum | 0x36 (54) |
| Array | 0x37 (55) |
| Dict/Map | 0x38 (56) |
| Image | 0x39 (57) |
| BANG | 0x3a (58) |
| timetag | 0x3b (59) |
| bin8? | |
| bin16? | |
| bin32? | |


## Typedefinition Boolean:

a 1-byte value. 0 == false, >0 == true

## Typedefinition Number (uint8, int8, uint16, int16, ...):

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| min | 0x31 (49) | of type | 0 | y | min value
| max | 0x32 (50) | of type | 0 | y | max value
| multipleof | 0x33 (51) | of type | 0 | y | multiple of value
| scale | 0x34 (52) | 1 byte | 0 | < | one of these (0x00, 0x01, 0x02)
| unit | 0x35 (53) | tiny-string | "" | y | the unit of value

## Typedefinition Vector (Vector2f32, Vector2i8, Vector4f32, ...):

VectorXY
where X specifies the size
where Y specifies the type

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| min | 0x31 (49) | X times type | 0 | y | min value
| max | 0x32 (50) | X times type | 0 | y | max value
| multipleof | 0x33 (51) | X times type | 0 | y | multiple of value
| scale | 0x34 (52) | 1 byte | 0 | < | one of these (0x00, 0x01, 0x02)
| unit | 0x35 (53) | tiny-string | "" | y | the unit of value

### scale table

| Name   | hex   |
|--------|-------|
| Linear | 0x00 |
| Log | 0x01 |
| exp2 | 0x02 |

## Typedefinition String

a long-string. 32bit size-prefixed UTF-8 string

## Typedefinition Enum

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| entries | 0x31 (49) | 16bit number of followed by tiny-strings | 0 | y | list of enumerations

## Typedefinition Array

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| subtype | 0x31(49) | TypeDefinition | Type String | y | TypeDefintion of array elements


## Widget (0x24):

| Name          | ID hex/dec   | Type           | default value   | optional   | description   |
| --------------|--------------|----------------|-----------------|------------|---------------|
| type | 0x50 (80) | 2 byte | text input | y | type of widget.  see widget type-table
| enabled | 0x51 (81) | 1 byte | true | y | if widget allows user input
| visible | 0x52 (82) |	1 byte | true | y | if widget is visible
| label-visible | 0x53	(83) | 1 byte | true | y | if label is visible
| value-visible | 0x54 (84) | 1 byte | true | y | if value is visible
| label-position | 0x55 (85) | 1 byte | 0 | y | see label-position table
| **terminator** | 0 | 1 byte | 0 | n | terminator

### Widget type table:

| typename   | hex   |
|------------|-------|
| Textbox | 0x10 |
| Numberbox | 0x11 |
| Button | 0x12 |
| Checkbox | 0x13 |
| Radiobutton | 0x14 |
| Slider | 0x15 |
| Dial | 	0x16 |
| Colorbox | 0x17 |
| Table | 0x18 |
| Treeview | 0x19 |
| Dropdown | 0x1a |
| XYField | 0x1b |

### label-position table:

| typename   | hex   |
|------------|-------|
| left | 0x00 |
| right | 0x01 |
| top | 0x02 |
| bottom | 0x03 |
| center | 0x04 |
