import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public final class Context {

    private static String responseMessage = "-";
    private static String movieData;

    public Context(String jsonPath){
        movieData = "";
        try{
            Files.lines(Paths.get(jsonPath), StandardCharsets.UTF_8).forEach(line -> {
                movieData+=line+"\n";
            });
        } catch(Exception e){
            System.out.println("Failed to open file: "+e);
        }
    }

    public static void setResponseMessage(String i){ responseMessage = i; }
    public static String getResponseMessage(){ return responseMessage; }
    public static String getMovieData(){ return movieData; }

}