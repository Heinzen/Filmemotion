var posterArray = [];

function initTopMovies() {
    console.log("Initializing top movies");
    sendSocketMessage(JSON.stringify(getTopMoviesRequest));
}

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
        var poster = new Poster(jsonData[i].URL, jsonData[i].ID, jsonData[i].Title);
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
    var indexOfClicked = search(e.currentTarget.getAttribute("posterId"));
    posterArray[indexOfClicked].state = !posterArray[indexOfClicked].state;
    var htmlElement = document.querySelector('[posterId="'+posterArray[indexOfClicked].id+'"');
    if(htmlElement.getAttribute("enabled") == "true") {
        console.log("Is enabled, disabling");
        htmlElement.setAttribute("enabled", "false");
    } else { 
        console.log("Is disabled, enabling");
        htmlElement.setAttribute("enabled", "true");
    }
}

window.onclick = e => {} 