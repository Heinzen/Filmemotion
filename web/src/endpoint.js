function processImage() {
    
    var APIkey = "029ae02d0838491786d77f9b91a836ef";
    var uriBase = "https://brazilsouth.api.cognitive.microsoft.com/face/v1.0/detect";
        //"returnFaceId": "true",
        //"returnFaceLandmarks": "false",
    var params = {
        "returnFaceAttributes":
            "age,gender,emotion",
        "recognitionModel": "recognition_02"
    };

    var blob = makeBlob(imageByte64)
    console.log(blob);

    $.ajax({
        url: uriBase + "?" + $.param(params),

        // Request headers.
        beforeSend: function(xhrObj){
            xhrObj.setRequestHeader("Content-Type","application/octet-stream");
            xhrObj.setRequestHeader("Ocp-Apim-Subscription-Key", APIkey);
        },

        type: "POST",
        // Since its not a JSON we need these
        // (took me forever)
        processData: false,
        contentType: false,

        // Request body.
        data: blob,
    })

    .done(function(data) {
        //$("#responseTextArea").val(JSON.stringify(data, null, 2));
        alert(JSON.stringify(data,null,2)) ;
        buildDataRequest(data);
    })

    .fail(function(jqXHR, textStatus, errorThrown) {
        var errorString = (errorThrown === "") ?
            "Error. " : errorThrown + " (" + jqXHR.status + "): ";
        errorString += (jqXHR.responseText === "") ?
            "" : (jQuery.parseJSON(jqXHR.responseText).message) ?
                jQuery.parseJSON(jqXHR.responseText).message :
                    jQuery.parseJSON(jqXHR.responseText).error.message;
        alert(errorString);
    });
};

function makeBlob(dataURL) {
    var BASE64_MARKER = ';base64,';
    if (dataURL.indexOf(BASE64_MARKER) == -1) {
        var parts = dataURL.split(',');
        var contentType = parts[0].split(':')[1];
        var raw = decodeURIComponent(parts[1]);
        return new Blob([raw], { type: contentType });
    }
    var parts = dataURL.split(BASE64_MARKER);
    var contentType = parts[0].split(':')[1];
    var raw = window.atob(parts[1]);
    var rawLength = raw.length;

    var uInt8Array = new Uint8Array(rawLength);

    for (var i = 0; i < rawLength; ++i) {
        uInt8Array[i] = raw.charCodeAt(i);
    }

    return new Blob([uInt8Array], { type: contentType });
}