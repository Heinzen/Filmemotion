//Context variables
var userId;

//Requests
var getPosterRequest = {
    "request-type"  : "GET",
    "parameter"     : "POSTER",
}
var getTopMoviesRequest = {
    "request-type"  : "GET",
    "parameter"     : "TOPMOVIES",
}

var postUserId = {
    "request-type"  : "POST",
    "parameter"     : "userId",
    "value"         : 0
}

var postUserEmotionData = {
    "request-type"  : "POST",
    "parameter"     : "userEmotionData",
    "value"         : ""
}

var postAcceptedMovie = {
    "request-type"  : "POST",
    "parameter"     : "acceptMovie",
    "value"         : 0
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
        case "recommendedmovies":
            createRecommendationList(jsonData);
            createCarrousel();
            break;
    }
}

function buildDataRequest(message){
    postUserEmotionData.value = message;
    sendSocketMessage(JSON.stringify(postUserEmotionData));
}

function getQueryVariable(variable){
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] == variable){return pair[1];}
    }
    return(false);
}

function initUserData(){
    userId = getQueryVariable("id");
    console.log("Initializing user data for user: "+userId);
    postUserId.value = userId;
    sendSocketMessage(JSON.stringify(postUserId));
}

function initTopMovies() {
    console.log("Initializing top movies");
    sendSocketMessage(JSON.stringify(getTopMoviesRequest));
}

function createMoviePosters(data){
    parseMovieData(data);
    createPoster();
}

function parseMovieData(inputData) {
    for(var i = 1; i < inputData.length; i++) {
        jsonData[i] = inputData[i];
    }
}

function handleAcceptingMovie(){
    postAcceptedMovie.value = selectedMovie;
    console.log(postAcceptedMovie);
    sendSocketMessage(JSON.stringify(postAcceptedMovie));
}