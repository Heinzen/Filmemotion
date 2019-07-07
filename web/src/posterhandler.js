var posterArray = [];
var selectedMovie;

function Poster(imgURL, ID, title) {
    this.state = false;
    this.id = ID;
    this.imgURL = imgURL;
    this.title = title;
    this.append = function(){
        $("#posterContainer").append('<img src="'+this.imgURL+'" posterId="'+this.id+'" enabled=true class="poster" onClick="handlePosterClick(event)"/>');
    }
 }

function createPoster() {
    for(var i = 1; i < jsonData.length; i++){
        var poster = new Poster(jsonData[i].Poster, jsonData[i].ID, jsonData[i].Title);
        poster.append();
        posterArray.push(poster);
    }
}

function search(id){
    for (var i=0; i < posterArray.length; i++) {
        if (posterArray[i].id == id) {
            return i;
        }
    }
}

function handlePosterClick(e) { 
    if(selectedMovie != e.getAttribute("posterid"))
        selectedMovie = e.getAttribute("posterid");
    else
        selectedMovie = 0;

    var highestId = 0;
    for(var i = 0; i < recommendedMovies.length; i++) {
        if(recommendedMovies[i].id > highestId)
            highestId = recommendedMovies[i].id;
    }
    
    console.log("Selected: "+selectedMovie);
    addBlurOverPosters(selectedMovie, highestId);
    if(selectedMovie == 0) {
        removeBlurFromAll(highestId);
        handleControls(3);
    }
    else {
        removeBlurFromSelected(selectedMovie, highestId);
        handleControls(4);
    } 
}

function addBlurOverPosters(id, highestId) {
    var notSelectedMovies = [];
    var j = 0;

    for(var i = 0; i <= highestId; i++){
        var nodes = document.querySelectorAll('[posterid="'+i+'"]');
        if(typeof recommendedMovies[i] !== "undefined" && recommendedMovies[i].id == id) {
            continue;
        }
        
        if(nodes.length > 0)
            notSelectedMovies[j++] = nodes;
    }
    
    notSelectedMovies.forEach(function(item) {
        item[0].classList.add("blur");
    });
}

function removeBlurFromSelected(id) {
    if(id != 0){
        var node = document.querySelectorAll('[posterid="'+id+'"]');
        node[0].classList.remove("blur");
    }
}

function removeBlurFromAll(highestId) {
    var notSelectedMovies = [];
    var j = 0;

    for(var i = 0; i <= highestId; i++){
        var nodes = document.querySelectorAll('[posterid="'+i+'"]');
        if(nodes.length > 0)
            notSelectedMovies[j++] = nodes;
    }
    
    notSelectedMovies.forEach(function(item) {
        item[0].classList.remove("blur");
    });
}

window.onclick = e => {} 