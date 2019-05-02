document.addEventListener("DOMContentLoaded",init);

var videoWidth = 450;  
var is_Chrome =  /Chrome/.test(navigator.userAgent);
var is_Firefox = /firefox/i.test(navigator.userAgent);
var webcam;

function init(){
    var canvas = document.getElementById("canvas");
    var context = canvas.getContext("2d");
    var eventSelector = document.getElementById("btn_snap");
    
    eventSelector.addEventListener('click', captureImage, false);
    canvas.style.width = videoWidth + "px";
    eventSelector.context = context;
    startWebcam();
}

function startWebcam(){
    webcam = document.querySelector("#webcam");
    webcam.width = videoWidth;
    
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mediaDevices || navigator.mozGetUserMedia || navigator.msGetUserMedia || navigator.oGetUserMedia;

    if (navigator.mediaDevices){
    navigator.mediaDevices.getUserMedia({video: true}, handleVideo, videoError).then(function(stream){
        webcam.onloadedmetadata = setHeight;
        document.getElementById("btn_snap").disabled = false;
        return webcam.srcObject = stream;
    }).catch(function(e){
        console.log(e.name + ": "+ e.message);
        document.getElementById("btn_snap").disabled = true;
    });
    }
}

function videoError(e){
}

function captureImage(params){
    if(document.getElementById("canvas").style.display == "none" || document.getElementById("canvas").style.display == ""){
        params.target.context.drawImage(webcam, 0, 0, webcam.videoWidth, webcam.videoHeight);
        console.log(stream);
        handleControls(1);
    }
    else
        handleControls(2);
}

function handleControls(i){
    switch(i){
        case 1:
            document.getElementById("canvas").style.display = "block";
            document.getElementById("btn_snap").innerHTML = "Retake";
            document.getElementById("btn_save").innerHTML = "Save";
            document.getElementById("btn_save").disabled = false;
            break;
        case 2:
            document.getElementById("canvas").style.display = "none";
            document.getElementById("btn_snap").innerHTML = "Capture";
            document.getElementById("btn_save").innerHTML = "Save";
            document.getElementById("btn_save").disabled = true;
            break;
    }
}

function handleVideo(stream){
    webcam.src = window.URL.createObjectURL(stream);
}

function setHeight(){
    canvas.width = webcam.videoWidth;
    canvas.height = webcam.videoHeight;
}