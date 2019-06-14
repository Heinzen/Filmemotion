//Requests
var getPosterRequest = {
    "request-type"  : "GET",
    "parameter"     : "POSTER",
}
var getTopMoviesRequest = {
    "request-type"  : "GET",
    "parameter"     : "TOPMOVIES",
}

//Variables
var jsonData = new Array();

//Functions
function parseReply(message){
    var jsonData = JSON.parse(message);
    var responseKey = jsonData[0];
    console.log("Received request: "+jsonData[0].key);
    switch(responseKey.key){
        case "poster":
            createMoviePosters(jsonData);
            break;
        case "topmovies":
            createMoviePosters(jsonData);
            break;
    }
}

function createMoviePosters(data){
    parseMovieData(data);
    createPoster();
}

function parseMovieData(inputData) {
    for(var i = 1; i < inputData.length; i++) {
        //console.log(inputData[i]);
        jsonData[i] = inputData[i];
    }
}