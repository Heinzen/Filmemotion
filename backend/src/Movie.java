public class Movie {
    private String title;
    private String[] genre;
    private Double imdbScore;
    private int metacriticScore;
    private String poster;
    private String mpaa;
    private int id;

    Movie(String t, Double i, int m, String p, String[] g, int id, String mpaa){
        this.title = t;
        this.imdbScore = i;
        this.metacriticScore = m;
        this.poster = p;
        this.genre = g;
        this.id = id;
        this.mpaa = mpaa;
    }
    public String getTitle(){ return this.title; }
    public Double getImdbScore(){ return this.imdbScore; }
    public int getMetacriticScore(){ return this.metacriticScore; }
    public String getPoster(){ return this.poster; }
    public String[] getGenre(){ return this.genre; }
    public int getId(){ return this.id; }
    public String getMpaa(){ return this.mpaa; }

    @Override
    public String toString() {
        return "\nTitle: "+this.title+
                " | Genre: "+this.genre[0]+
                " | IMDb Score: "+this.imdbScore+
                " | MPAA: "+this.mpaa;
    }
}
