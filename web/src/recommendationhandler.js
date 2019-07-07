var recommendedMovies = [];
var movieGenres = [];

function Movie(poster, id, title, imdb, genre) {
    this.accepted = false;
    this.id = id;
    this.poster = poster;
    this.title = title;
    this.genre = genre;
    this.rating = imdb;
 }

 function createRecommendationList(inputData){
    var j = 0;
    for(var i = 1; i < inputData.length; i++){
        if(!movieGenres.includes(inputData[i].genre[0])){
            movieGenres[j++] = inputData[i].genre[0];
        }
        var movie = new Movie(inputData[i].poster, inputData[i].id, inputData[i].title, inputData[i].imdb, inputData[i].genre);
        recommendedMovies.push(movie);
    }
}