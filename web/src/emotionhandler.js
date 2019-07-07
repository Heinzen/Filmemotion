var parsedEmotion;

function doSomething(){
    delete parsedEmotion[0].faceId;
    delete parsedEmotion[0].faceRectangle;
    parsedEmotion[0].userId = 12345;
    console.log(parsedEmotion);
}

var snapEventSelector = document.getElementById("btn_emotion");
document.addEventListener("DOMContentLoaded",init_emotion);
  
function init_emotion(){
    //goEventSelector.addEventListener('click', postImage, false);
}
