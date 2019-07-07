import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class UserParser {

    private static int userId;
    private static String jsonPath;
    private static String currentMoodKey;
    private static HashMap<Context.GENRE, Double> coefficientMap;
    private static String userData;
    private static String userHistory;
    private static int dampeningFactor;
    private static boolean pg = false;

    private static final String defaultUserDataJson = "resources/default_userdata.json";
    private static final String defaultUserHistoryJson = "resources/default_userhistory.json";

    public UserParser(String value, int d) {
        userId = Integer.valueOf(value);
        coefficientMap = new HashMap<>();
        jsonPath = "resources/"+value;
        dampeningFactor = d;
        parseUserData();
        parseUserHistory();
    }

    public static void parseUserData(){
        userData = "";
        try{
            if(Files.exists(Paths.get(jsonPath), LinkOption.NOFOLLOW_LINKS)){
                Files.lines(Paths.get(jsonPath+"/userdata.json"), StandardCharsets.UTF_8).forEach(line -> {
                    userData+=line+"\n";
                });
            }
            else {
                new File(jsonPath).mkdir();
                try {
                    System.out.println(Files.exists(Paths.get(defaultUserDataJson)));
                    Path sourceFile = Paths.get(defaultUserDataJson);
                    Path destinationFolder = Paths.get(jsonPath);

                    Files.lines(Paths.get(defaultUserDataJson), StandardCharsets.UTF_8).forEach(line -> {
                        userData+=line+"\n";
                    });

                    Files.copy(sourceFile, destinationFolder.resolve("userdata.json"), StandardCopyOption.REPLACE_EXISTING);
                    updateUserIdInFile();
                } catch(IOException e) {
                    System.out.println("Failed to copy file: "+e);
                }
            }
        } catch(Exception e){
            System.out.println("Failed to open file: "+e);
        }
    }
    private static void parseUserHistory() {
        userHistory = "";
        try {
            if (Files.exists(Paths.get(jsonPath+"/userhistory.json"), LinkOption.NOFOLLOW_LINKS)) {
                Files.lines(Paths.get(jsonPath + "/userhistory.json"), StandardCharsets.UTF_8).forEach(line -> {
                    userHistory += line + "\n";
                });
            } else {
                new File(jsonPath).mkdir();
                try {
                    System.out.println(Files.exists(Paths.get(defaultUserHistoryJson)));
                    Path sourceFile = Paths.get(defaultUserHistoryJson);
                    Path destinationFolder = Paths.get(jsonPath);

                    Files.lines(Paths.get(defaultUserHistoryJson), StandardCharsets.UTF_8).forEach(line -> {
                        userHistory += line + "\n";
                    });

                    Files.copy(sourceFile, destinationFolder.resolve("userhistory.json"), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.out.println("Failed to copy file: " + e);
                }
            }
        } catch(Exception e){
            System.out.println("Failed to open file: "+e);
        }
    }
    private static void updateUserIdInFile(){
        try {
            System.out.println("Replacing JSON entry for user: "+userId);
            JSONObject allEntries = new JSONObject(userData);

            allEntries.put("USERID", userId);
            //System.out.println(allEntries.toString(4));
            FileWriter f = new FileWriter(jsonPath+"/userdata.json");
            f.write("");
            f.write(allEntries.toString(4));
            f.close();
        }catch(Exception e){System.out.println(e);}
    }
    private static void updateCoefficient(String genre, double coefficient){
        coefficientMap.put(Context.GENRE.valueOf(genre), coefficient);
    }
    public static HashMap<Context.GENRE, Double> getMap(){ return coefficientMap; }
    public static void parseFaceAttributes(String input){
        try{
            Pair<Context.EMOTION, Double> predominantEmotion = new Pair<>(Context.EMOTION.NEUTRAL, (double) 0);

            JSONArray array = new JSONArray(input);
            JSONObject faceAttributes = array.getJSONObject(0).getJSONObject("faceAttributes");

            //verifies if its a PG user
            pg = EmotionEngine.isPg(faceAttributes.getInt("age"));


            //Determines the most predominant emotion for input data
            Iterator<String> keys = faceAttributes.getJSONObject("emotion").keys();
            while(keys.hasNext()){
                String key = keys.next();
                JSONObject current = faceAttributes.getJSONObject("emotion");

                if(current.getDouble(key) > predominantEmotion.getValue())
                    predominantEmotion = new Pair<>(Context.EMOTION.valueOf(key.toUpperCase()), current.getDouble(key));
            }
            currentMoodKey = predominantEmotion.getKey().toString();
        }catch(Exception e){ System.out.println(e); }
    }
    public static void buildUserDataForMood(){
        try{
            JSONTokener tokener = new JSONTokener(userData);
            JSONObject object = (JSONObject) tokener.nextValue();
            JSONObject childObject = (JSONObject) object.get(currentMoodKey);

            userId = (int) object.get("USERID");

            Iterator it = childObject.keys();
            while(it.hasNext()){
                String key = (String) it.next();
                double coefficient = childObject.getDouble(key);
                coefficientMap.put(Context.GENRE.valueOf(key), coefficient);
            }
        }catch(Exception e){ System.out.println("Failed to parse JSON: "+e); }
    }
    private static void rejectGenre(String genre){
        double u;
        double w = EmotionEngine.getGenreWeighting(Context.EMOTION.valueOf(currentMoodKey), Context.GENRE.valueOf(genre));
        double _u = coefficientMap.get(Context.GENRE.valueOf((genre)));

        u = _u*((_u+w)/dampeningFactor);

        if(u < 0.1)
            u = 0.1;

        updateCoefficient(genre, u);
    }
    private static void acceptGenre(String genre){
        double u;
        double w = EmotionEngine.getGenreWeighting(Context.EMOTION.valueOf(currentMoodKey), Context.GENRE.valueOf(genre));
        double _u = coefficientMap.get(Context.GENRE.valueOf(genre));

        u = _u/Math.pow(Math.E, Math.log(w)/(2*dampeningFactor));

        if(u > 1)
            u = 1;

        updateCoefficient(genre, u);
    }
    private static void updateJsonRecord(){
        try {
            JSONObject updatedEntry = new JSONObject(coefficientMap);
            JSONObject allEntries = new JSONObject(userData);

            allEntries.put(currentMoodKey, updatedEntry);

            FileWriter f = new FileWriter(jsonPath+"/userdata.json");
            f.write("");
            f.write(allEntries.toString(4));
            f.close();
        }catch(Exception e){}
    }
    private static boolean isColdStart(){
        for(Map.Entry<Context.GENRE, Double> it : coefficientMap.entrySet()){
            if(it.getValue() != 1)
                return false;
        }
        return true;
    }
    public static void getGenreForAcceptance(int acceptedMovieId){
        Context.GENRE g;
        Movie m = Context.getMovieById(acceptedMovieId);
        if(m != null) {
            g = Context.GENRE.valueOf(m.getGenre()[0].toUpperCase());
            updateContentWeight(g);
        }
        saveMovieIntoHistory(acceptedMovieId);
    }
    private static void updateContentWeight(Context.GENRE g){
        for(Map.Entry<Context.GENRE, Double> it : coefficientMap.entrySet()){
            if(it.getKey().toString() != g.toString())
                rejectGenre(it.getKey().toString());
        }
        acceptGenre(g.toString());

        updateJsonRecord();
    }
    public static boolean checkIfMovieIsInHistory(int id){
        try {
            JSONObject obj = new JSONObject(userHistory);
            JSONArray array = obj.getJSONArray("watched");
            for(int i = 0; i < array.length(); i++){
                if(array.getInt(i) == id)
                    return true;
            }
        } catch(Exception e) { System.out.println(e); }
        return false;
    }
    public static void selectContent(int top, int bottom){
        HashMap<Context.GENRE, Double> aux;
        if(isColdStart()){
            aux = EmotionEngine.getMapForEmotion(Context.EMOTION.valueOf(currentMoodKey));
        } else{
            aux  = coefficientMap;
        }

        ArrayList<Movie> outputList = new ArrayList<>();

        for(Map.Entry<Context.GENRE, Double> e : aux.entrySet()){
            System.out.println("Coefficient for "+e.getKey().toString()+" is "+e.getValue());
            if(e.getValue() > 0.5){
                System.out.println("Including "+top+" "+e.getKey().toString()+" movies");
                for(int i = 0; i < top; i++){
                    Movie auxMovie = Context.getTopMovieByIndex(e.getKey(), i);
                    if(checkIfMovieIsInHistory(auxMovie.getId())){
                        top++;
                    } else {
                        outputList.add(auxMovie);
                    }

                }
            } else if(e.getValue() > 0.1 && e.getValue() <= 0.5){
                System.out.println("Including "+bottom+" "+e.getKey().toString()+" movies");
                for(int i = 0; i < bottom; i++){
                    Movie auxMovie = Context.getTopMovieByIndex(e.getKey(), i);
                    if(checkIfMovieIsInHistory(auxMovie.getId())){
                        bottom++;
                    } else {
                        outputList.add(auxMovie);
                    }
                }
            }
        }
        buildOutputMovieJson(outputList);
    }
    private static void buildOutputMovieJson(ArrayList<Movie> input){
        JSONArray output = new JSONArray();
        try{
            JSONObject key = new JSONObject();
            key.put("key", "recommendedmovies");
            output.put(key);
            for(Movie m : input){
                JSONObject aux = new JSONObject();
                aux.put("id", m.getId());
                aux.put("title", m.getTitle());
                aux.put("poster", m.getPoster());
                aux.put("genre", m.getGenre());
                aux.put("imdb", m.getImdbScore());
                output.put(aux);
            }
        } catch(Exception e){ System.out.println(e); }
        Context.setResponseMessage(output.toString());
    }
    public static void saveMovieIntoHistory(int id){
        try {
            JSONObject obj = new JSONObject(userHistory);
            JSONArray array = obj.getJSONArray("watched");

            array.put(id);
            obj.put("watched", array);

            FileWriter f = new FileWriter(jsonPath+"/userhistory.json");
            f.write("");
            f.write(obj.toString(4));
            f.close();
        }catch(Exception e){ System.out.println(e); }
    }
}