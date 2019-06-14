import org.json.JSONArray;
import org.json.JSONObject;

public final class MessageParser {
    private MessageParser(){}

    private static String requestType;

    public static void parseParameters(String input) {
        try{
            System.out.println("Parsing JSONObject");
            JSONObject message = new JSONObject(input);
            requestType = message.getString("request-type");
            String parameter;
            switch(requestType){
                case "GET":
                    parameter = message.getString("parameter");
                    handleFunctionCalls(parameter);
                    break;
            }
        }catch (Exception e){ }
    }

    private static void handleFunctionCalls(String message){
        if(message.equalsIgnoreCase("poster")){
            JSONArray posterResponse = MovieFactory.getMoviePosters();
            System.out.println(posterResponse.toString());
            Context.setResponseMessage(posterResponse.toString());
        } else if(message.equalsIgnoreCase("topmovies")) {
            JSONArray posterResponse = MovieFactory.getTopMovies();
            System.out.println(posterResponse.toString());
            Context.setResponseMessage(posterResponse.toString());
        }
    }
}
