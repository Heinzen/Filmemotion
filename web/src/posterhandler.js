var posterArray = [];

function Poster(imgURL) {
    this.id = posterArray.length;
    this.state = false;
    this.imgURL = posters[this.id];
    this.append = function(){
        $("#posterContainer").append('<img src="'+this.imgURL+'" posterId="'+this.id+'" enabled=true class="poster" onClick="handlePosterClick(event)"/>');
    }
 }


function createPoster() {
    var poster = new Poster("media/poster.jpg");
    poster.append();
    posterArray.push(poster);
}



function handlePosterClick(e) { 
    posterArray[e.currentTarget.getAttribute("posterId")].state = !posterArray[e.currentTarget.getAttribute("posterId")].state;
    var htmlElement = document.querySelector('[posterId="'+posterArray[e.currentTarget.getAttribute("posterId")].id+'"');
    if(htmlElement.getAttribute("enabled") == "true") {
        console.log("Is enabled, disabling");
        htmlElement.setAttribute("enabled", "false");
    } else { 
        console.log("Is disabled, enabling");
        htmlElement.setAttribute("enabled", "true");
    }
}

window.onclick = e => {
    
} 