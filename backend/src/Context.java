import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public final class Context {

    private static String responseMessage = "";
    private static String rawMovieData;
    private static Vector<Movie> movies;
    private static HashMap<Context.GENRE, ArrayList<Movie>> sortedTopMoviesByGenre;
    private static final int top_content_amount = 4;
    private static final int bottom_content_amount = 2;
    private static final int dampening_factor = 3;

    enum EMOTION{
        HAPPINESS,
        ANGER,
        CONTEMPT,
        DISGUST,
        FEAR,
        NEUTRAL,
        SADNESS,
        SURPRISE
    }
    enum GENRE{
        DRAMA,
        SCIFI,
        THRILLER,
        ANIMATION,
        COMEDY,
        ROMANCE,
        ACTION,
        HORROR
    }

    public Context(String jsonPath){
        rawMovieData = "";
        try{
            Files.lines(Paths.get(jsonPath), StandardCharsets.UTF_8).forEach(line -> {
                rawMovieData +=line+"\n";
            });
        } catch(Exception e){
            System.out.println("Failed to open file: "+e);
        }
        buildMovieObjectData();
    }
    public static void setResponseMessage(String i){ responseMessage = i; }
    public static String getResponseMessage(){ return responseMessage; }
    public static String getRawMovieData(){ return rawMovieData; }
    public static ArrayList<Movie> getSortedTopMoviesByGenre(Context.GENRE g){ return sortedTopMoviesByGenre.get(g); }
    public static Movie getMovieById(int id){
        for(Movie m : movies){
            if(m.getId() == id)
                return m;
        }
        return null;
    }
    public static Movie getTopMovieByIndex(Context.GENRE g, int index){ return sortedTopMoviesByGenre.get(g).get(index); }
    private static void buildMovieObjectData(){
        movies = new Vector<>();

        try{
            JSONTokener tokener = new JSONTokener(rawMovieData);
            System.out.println(tokener.toString());
            JSONArray array = (JSONArray) tokener.nextValue();

            for(int index = 0; index < array.length(); index++){
                JSONObject auxmovie = array.getJSONObject(index);
                String[] genreArray = new String[3];
                genreArray[0] = auxmovie.getString("MainGenre");
                genreArray[1] = auxmovie.getString("SubGenre1");
                genreArray[2] = auxmovie.getString("SubGenre2");

                movies.add(new Movie(
                        auxmovie.getString("Title"),
                        auxmovie.getDouble("imdb"),
                        auxmovie.getInt("MetaCritic"),
                        auxmovie.getString("Poster"),
                        genreArray,
                        auxmovie.getInt("ID"),
                        auxmovie.getString("MPAA")
                ));
            }
        }catch(Exception e){ System.out.println("Failed to parse JSON: "+e); }
        buildTopMovieByGenre();
    }
    private static void buildTopMovieByGenre() {
        sortedTopMoviesByGenre = new HashMap<>();
        for(Context.GENRE g : Context.GENRE.values()){
            ArrayList<Movie> aux = new ArrayList<>();
            for(Movie m : movies) {
                if (m.getGenre()[0].toLowerCase().equals(g.toString().toLowerCase()))
                    aux.add(m);
            }
            aux.sort(Comparator.comparing(Movie::getMetacriticScore).reversed());
            sortedTopMoviesByGenre.put(g,aux);
        }
    }
    public static int getTopContentAmount(){ return top_content_amount; }
    public static int getBottomContentAmount(){ return bottom_content_amount; }
    public static int getDampeningFactor(){ return dampening_factor; }
}