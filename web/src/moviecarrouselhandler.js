var interval;

function scrollR(){
    interval = setInterval(function(){ document.getElementById('scroller').scrollLeft += 1 }  , 5);
}
function scrollL(){
    interval = setInterval(function(){ document.getElementById('scroller').scrollLeft -= 1 }  , 5);
}
function clearScroll(){
  clearInterval(interval);
}

var movieDivForCarrousel = [];

function createCarrousel(){
    var longHtml = createMovieDivForCarrousel();
    $(".contain").append(longHtml);
}

function createMovieDivForCarrousel(){
    var carrousel = '';
    for(var j = 0; j < movieGenres.length; j++){
        //creates a slider, section title and defines mouseovers
        carrousel += '<div class="slider"><h3>'+movieGenres[j]+'</h3><span onmouseover="scrollL()" onmouseout="clearScroll()" class="handle handlePrev active"><i class="fa fa-caret-left" aria-hidden="true"></i></span><div id="scroller" class="row"><div class="row__inner">';
        for(var i = 0; i < recommendedMovies.length; i++){
            if(recommendedMovies[i].genre[0].toLowerCase() == movieGenres[j].toLowerCase()){
                carrousel +='<div class="gui-card"><div class="gui-card__media"><img class="gui-card__img none" src="'+recommendedMovies[i].poster+'" posterId="'+recommendedMovies[i].id+'"/></div><div class="gui-card__details" posterId="'+recommendedMovies[i].id+'" onClick="handlePosterClick(this)"><div class="gui-card__title">'+recommendedMovies[i].title+'</div></div></div>';
            }
        }
        carrousel += '</div></div><span onmouseover="scrollR()" onmouseout="clearScroll()"  class="handle handleNext active"><i class="fa fa-caret-right" aria-hidden="true"></i></span></div>';
    }
    return carrousel;
}