import org.json.JSONArray;

import org.json.JSONObject;
import org.json.JSONTokener;

public final class MovieFactory {
    public MovieFactory(){
        moviePosters = new JSONArray();
        topMovies = new JSONArray();
    }

    private static JSONArray moviePosters;
    private static JSONArray topMovies;


    private static void buildAllPosters(){
        try{
            JSONTokener tokener = new JSONTokener(Context.getMovieData());
            JSONArray array = (JSONArray) tokener.nextValue();

            JSONObject key = new JSONObject();
            key.put("key", "poster");
            moviePosters.put(key);
            for(int index = 0; index < array.length(); index++){
                JSONObject current = (JSONObject) array.get(index);
                JSONObject subJson = new JSONObject();

                subJson.put("ID", current.getInt("ID"));
                subJson.put("Title", current.get("Title"));
                subJson.put("URL", current.get("Poster"));

                moviePosters.put(subJson);
            }
        }catch(Exception e){ System.out.println("Failed to parse JSON: "+e); }
    }

    private static void buildTopMovies() {
        try{
            JSONTokener tokener = new JSONTokener(Context.getMovieData());
            JSONArray array = (JSONArray) tokener.nextValue();

            JSONObject key = new JSONObject();
            key.put("key", "topmovies");
            topMovies.put(key);
            //Arbitrarily decide on top10 using Metacritic
            for(int index = 0; index < 10; index++){
                JSONObject current = (JSONObject) array.get(index);
                JSONObject subJson = new JSONObject();

                subJson.put("ID", current.getInt("ID"));
                subJson.put("Title", current.get("Title"));
                subJson.put("Rating", current.get("MetaCritic"));
                subJson.put("URL", current.get("Poster"));

                topMovies.put(subJson);
            }
        }catch(Exception e){ System.out.println("Failed to parse JSON: "+e); }
    }

    public static JSONArray getMoviePosters(){
        if(moviePosters.length() == 0){ buildAllPosters(); }

        return moviePosters;
    }

    public static JSONArray getTopMovies(){
        if(topMovies.length() == 0){ buildTopMovies(); }

        return topMovies;
    }
}

//System.out.println("Current Movie: "+current.get("ID").toString()+" "+current.get("Title").toString());