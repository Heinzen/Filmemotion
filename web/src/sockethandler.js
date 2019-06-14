var ws_protocol = "ws";
var ws_hostname = "localhost";
var ws_port = "4444";
var ws_endpoint = "";
var webSocket = null;

document.addEventListener("DOMContentLoaded",startWebSocket);

function startWebSocket() {
    openWSConnection(ws_protocol, ws_hostname, ws_port, ws_endpoint);
}

function disconnectWebSocket() {
    webSocket.close();
}

/**
* Open a new WebSocket connection using the given parameters
*/
function openWSConnection(protocol, hostname, port, endpoint) {
    var webSocketURL = null;
    webSocketURL = protocol + "://" + hostname + ":" + port + endpoint;
    console.log("openWSConnection::Connecting to: " + webSocketURL);
    try {
        webSocket = new WebSocket(webSocketURL);
        webSocket.onopen = function(openEvent) {
            console.log("WebSocket OPEN: " + JSON.stringify(openEvent, null, 4));
            initTopMovies();
        };
        webSocket.onclose = function (closeEvent) {
            console.log("WebSocket CLOSE: " + JSON.stringify(closeEvent, null, 4));
        };
        webSocket.onerror = function (errorEvent) {
            console.log("WebSocket ERROR: " + JSON.stringify(errorEvent, null, 4));
        };
        webSocket.onmessage = function (messageEvent) {
            var wsMsg = messageEvent.data;
            parseReply(wsMsg);
            if (wsMsg.indexOf("error") > 0) {
                console.log(wsMsg.error + "\r\n");
            }
        };
    } catch (exception) {
        console.error(exception);
    }
}
/**
* Send a message to the WebSocket server
*/
function sendSocketMessage(message) {
    if (webSocket.readyState != WebSocket.OPEN) {
        console.error("webSocket is not open: " + webSocket.readyState);
        return;
    }
    console.log("Sending: "+message);
    webSocket.send(message);
}
