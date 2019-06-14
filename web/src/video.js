document.addEventListener("DOMContentLoaded",init);

var videoWidth = 450;  
var is_Chrome =  /Chrome/.test(navigator.userAgent);
var is_Firefox = /firefox/i.test(navigator.userAgent);
var webcam;
var canvas;
var context;
var imageByte64;

function init(){
    var goEventSelector = document.getElementById("btn_go");
    var snapEventSelector = document.getElementById("btn_snap");
    
    
    canvas = document.getElementById("canvas");
    context = canvas.getContext("2d");
    canvas.style.width = videoWidth + "px";
    
    goEventSelector.addEventListener('click', postImage, false);
    snapEventSelector.addEventListener('click', captureImage, false);
    
    snapEventSelector.context = context;
    startWebcam();
}

function startWebcam(){
    webcam = document.querySelector("#webcam");
    webcam.width = videoWidth;
    
    navigator.getUserMedia = navigator.getUserMedia || navigator.webkitGetUserMedia || navigator.mediaDevices || navigator.mozGetUserMedia || navigator.msGetUserMedia || navigator.oGetUserMedia;

    if (navigator.mediaDevices){
    navigator.mediaDevices.getUserMedia({video: true}, handleVideo, null).then(function(stream){
        webcam.onloadedmetadata = setHeight;
        document.getElementById("btn_snap").disabled = false;
        return webcam.srcObject = stream;
    }).catch(function(e){
        console.log(e.name + ": "+ e.message);
        document.getElementById("btn_snap").disabled = true;
    });
    }
}

function captureImage(params){
    if(document.getElementById("canvas").style.display == "none" || document.getElementById("canvas").style.display == ""){
        params.target.context.drawImage(webcam, 0, 0, webcam.videoWidth, webcam.videoHeight);
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
            document.getElementById("btn_go").disabled = false;
            break;
        case 2:
            document.getElementById("canvas").style.display = "none";
            document.getElementById("btn_snap").innerHTML = "Capture";
            document.getElementById("btn_go").disabled = true;
            break;
    }
}

function postImage(){
    imageByte64 = canvas.toDataURL();
    processImage();
    
}

function handleVideo(stream){
    webcam.src = window.URL.createObjectURL(stream);
}

function setHeight(){
    canvas.width = webcam.videoWidth;
    canvas.height = webcam.videoHeight;
}