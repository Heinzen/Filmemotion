import org.json.JSONArray;
import org.json.JSONObject;

public final class MessageParser {
    private MessageParser(){}

    private static String requestType;

    public static void parseParameters(String input) {
        try{
            System.out.println("Parsing JSONObject");
            System.out.println(input);
            JSONObject message = new JSONObject(input);
            requestType = message.getString("request-type");
            String parameter;
            switch(requestType){
                case "GET":
                    parameter = message.getString("parameter");
                    handleFunctionCalls(parameter, null);
                    break;
                case "POST":
                    parameter = message.getString("parameter");
                    if(parameter.equalsIgnoreCase("userid")){
                        int userId = message.getInt("value");
                        handleFunctionCalls(parameter,""+userId);
                    }
                    else if(parameter.equalsIgnoreCase("userEmotionData")){
                        JSONArray j = message.getJSONArray("value");
                        handleFunctionCalls(parameter, j.toString());
                    }
                    else if(parameter.equalsIgnoreCase("acceptMovie")){
                        int acceptedMovieId = message.getInt("value");
                        handleFunctionCalls(parameter, ""+acceptedMovieId);
                    }
            }
        }catch (Exception e){ }
    }

    private static void handleFunctionCalls(String parameter, String value){
        //Request movie posters
        if(parameter.equalsIgnoreCase("poster")){
            JSONArray posterResponse = MovieFactory.getMoviePosters();
            System.out.println(posterResponse.toString());
            Context.setResponseMessage(posterResponse.toString());
        }
        //Requests top movies
        else if(parameter.equalsIgnoreCase("topmovies")) {
            JSONArray posterResponse = MovieFactory.getTopMovies();
            System.out.println(posterResponse.toString());
            Context.setResponseMessage(posterResponse.toString());
        }
        //Sets userID for current session
        else if(parameter.equalsIgnoreCase("userId")){
            System.out.println("Creating user for ID: "+value);
            new UserParser(value, Context.getDampeningFactor());
            Context.setResponseMessage(null);
        }
        //Parses user emotion
        else if(parameter.equalsIgnoreCase("userEmotionData")){
            UserParser.parseFaceAttributes(value);
            UserParser.buildUserDataForMood();
            UserParser.selectContent(Context.getTopContentAmount(), Context.getBottomContentAmount());
        }
        //Accepts movie
        else if(parameter.equalsIgnoreCase("acceptMovie")){
            UserParser.getGenreForAcceptance(Integer.parseInt(value));
            Context.setResponseMessage(null);
        }
    }
}
