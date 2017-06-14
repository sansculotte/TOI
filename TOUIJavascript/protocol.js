/*
*
*
*
*/
//------------------------------------
// in case Date is not supporting function now()
if (!Date.now) {
    Date.now = function() { return new Date().getTime(); }
}


//------------------------------------
// Protocol namespace
//------------------------------------
function Protocol() {}


// protocolize a string
Protocol.protocolizeString = function(str)
{
	// prepare object
	var theObject = {};
	theObject.data = str;

	// return UInt8 array or null
	return Protocol.protocolize(theObject);
}


Protocol.protocolize = function(theObject)
{
	// enconding obj to bytebuffer
	return JSON.stringify(theObject);

	// return UInt8 array or null
	//return encoded; // != false ? new Uint8Array(encoded) : null;
}


// deprotocolize an object
Protocol.deprotocolize = function(buffer)
{
	return JSON.parse(buffer);
}





//------------------------------------
// namespace oncore protocol TOI
//------------------------------------
function TOI() {}

TOI.init = function(host, port) {
  if (TOI.socket != null) {
    TOI.socket.close();
    TOI.socket.onopen = null;
    TOI.socket.onclose = null;
    TOI.socket.onerror = null;
    TOI.socket.onmessage = null;
    TOI.socket = null;
  }

  TOI.socket = new WebSocket("ws://" + host + ":" + port);
  //TOI.socket.binaryType = "arraybuffer";

  TOI.socket.onopen = function() {
     console.log('Connection open!');
  }

  TOI.socket.onclose = function() {
     console.log('Connection closed');
  }

  TOI.socket.onerror = function(error) {
     console.log('Connection error: ' + error);
  }

  TOI.socket.onmessage = function(msg) {
    var server_message = msg.data;
    console.log('message: ' + server_message);

    var packet = Protocol.deprotocolize(server_message);
    TOI.analyzeResponse(packet);
  }
}

TOI.defaultWidgetType = function(type) {
  if (this.type === "Integer") {

  } else if (this.type === "Boolean") {
    return TOI.WidgetTypes.CHECKBOX;
  } else if (this.type === "Integer") {
    return TOI.WidgetTypes.NUMBERBOX;
  } else if (this.type === "Long") {
    return TOI.WidgetTypes.NUMBERBOX;
  } else if (this.type === "Double") {
    return TOI.WidgetTypes.SLIDER;
  } else if (this.type === "String") {
    return TOI.WidgetTypes.LABEL;
  } else {
    console.log("type not supported: " + type);
  }

  return TOI.WidgetTypes.LABEL;
}

TOI.WidgetTypes = {};
TOI.WidgetTypes.LABEL = "Label";
TOI.WidgetTypes.BUTTON = "Button";
TOI.WidgetTypes.CHECKBOX = "Checkbox";
TOI.WidgetTypes.NUMBERBOX = "Numberbox";
TOI.WidgetTypes.SLIDER = "Slider";
TOI.WidgetTypes.COMBO = "Dropdown";
TOI.WidgetTypes.RADIO = "Radio";


TOI.commands = {};
TOI.commands.version = "version";
TOI.commands.init = "init";
TOI.commands.add = "add";
TOI.commands.update = "update";
TOI.commands.remove = "remove";



TOI.Packet = {};
TOI.Packet.command = "";
TOI.Packet.data = null;
TOI.Packet.timestamp = null;
TOI.Packet.id = null;

TOI.Parameter = {};
TOI.Parameter.id = ""; // mandatory
TOI.Parameter.type = null; // typedef
TOI.Parameter.value = null;
TOI.Parameter.label = null;
TOI.Parameter.description = null;
TOI.Parameter.group = null;
TOI.Parameter.order = null;
TOI.Parameter.widget = null;
TOI.Parameter.userdata = null;

TOI.TypeDefinition = {};
TOI.TypeDefinition.name = ""; // mandatory


TOI.newPacket = function(command, data, id) {
  var aPacket = {};
  aPacket.command = command;
  aPacket.data = data;
  aPacket.timestamp = Date.now();
  aPacket.id = id;

  return aPacket;
}

TOI.newInitPacket = function() {
  var aPacket = {};
  aPacket.command = TOI.commands.init;
  aPacket.timestamp = Date.now();

  return aPacket;
}


TOI.onAdd = function(parameter) {

}
TOI.onUpdate = function(parameter) {

}
TOI.onRemove = function(parameter) {

}


TOI.analyzeResponse = function(packet)
{
  // check if packet is an object
  if (packet === null || typeof packet !== 'object') {
    console.log("error: not an object");
  }


  // get command and status out of response
  var command = packet.command;
  var data = packet.data;
  var timestamp = packet.timestamp;
  var id = packet.id;


  //----------------------------
  // check for command
  if (command == undefined) {
    console.log("received without command: " + JSON.stringify(response));
    return;
  }

  if (command == TOI.commands.version) {
    console.log("version not implemented yet");
    return;
  }

  if (data == undefined) {
    console.log("no data for command: " + command);
    return;
  }


  // check if data is an array??

  // check if data is a parameter...
  // how strict do we want to be?
  param_id = data.id;
  if (param_id == null) {
    console.log("no id provided");
    return;
  }

  // be very strict with allowed fields...?
  for (var property in data) {
    if (data.hasOwnProperty(property)) {
      if (TOI.Parameter[property] === undefined) {
      	console.log("error: property not defined in Parameter: " + property);
        return;
      }
    }
  }

  // if it has a typedef, check mandatory
  if (data.type !== null) {
    if (data.type.name === null) {
      console.log("error: no name in typedef");
      return;
    }
  }


  switch (command) {
    case OCP.commands.add:
      console.log("add: " + JSON.stringify(data));
      TOI.onAdd(data);
      break;
    case OCP.commands.update:
      console.log("update: " + JSON.stringify(data));
      TOI.onUpdate(data);
      break;
    case OCP.commands.remove:
      console.log("remove: " + JSON.stringify(data));
      TOI.onRemove(data);
      break;
    default:
      console.log("unhandled command: " + command);
  }

  //----------------------------
  // got the data
  if (OCP.responseDataCB != null) {
    OCP.responseDataCB(command, data, messageID, timestamp);
  } else {
    console.log("no data callback set. got:");
    console.log("command: " + command);
    console.log("data: " + JSON.stringify(data));
  }

}
