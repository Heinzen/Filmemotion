import java.util.HashMap;

public class EmotionEngine {
    //flags
    private static final int PG_AGE = 13;
    private static HashMap<Context.EMOTION, HashMap<Context.GENRE, Double>> genreWeight;


    //Weights are compiled over the document with survey results
    public EmotionEngine(){
        genreWeight = new HashMap<>();
        buildGenreWeighting();
    }

    public static boolean isPg(int perceivedAge){ return perceivedAge <= PG_AGE; }
    private static void buildGenreWeighting(){
        System.out.println("SETTING UP EMOTION COEFFICIENTS");
        for(Context.EMOTION e : Context.EMOTION.values()){
            HashMap auxMap = new HashMap<Context.GENRE, Double>();
            for(Context.GENRE g : Context.GENRE.values()){
                //God's work
                switch(e){
                    case HAPPINESS:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.58);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.11);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.96);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.57);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.05);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.09);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.94);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.49);
                        }
                        break;

                    case ANGER:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.01);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.05);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.00);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.03);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.04);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.03);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.01);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.00);
                        }
                        break;

                    case DISGUST:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.00);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.02);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.00);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.04);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.04);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.04);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.00);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.01);
                        }
                        break;

                    case FEAR:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.07);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.02);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.00);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.01);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.81);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.46);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.00);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.09);
                        }
                        break;

                    case SADNESS:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.00);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.67);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.02);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.31);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.00);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.02);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.03);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.02);
                        }
                        break;

                    case SURPRISE:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.34);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.13);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.02);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.04);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.07);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.37);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.02);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.40);
                        }
                        break;

                    default:
                        if(g == Context.GENRE.ACTION){
                            auxMap.put(Context.GENRE.ACTION,0.00);
                        }
                        else if(g == Context.GENRE.DRAMA){
                            auxMap.put(Context.GENRE.DRAMA,0.00);
                        }
                        else if(g == Context.GENRE.COMEDY){
                            auxMap.put(Context.GENRE.COMEDY,0.00);
                        }
                        else if(g == Context.GENRE.ROMANCE){
                            auxMap.put(Context.GENRE.ROMANCE,0.00);
                        }
                        else if(g == Context.GENRE.HORROR){
                            auxMap.put(Context.GENRE.HORROR,0.00);
                        }
                        else if(g == Context.GENRE.THRILLER){
                            auxMap.put(Context.GENRE.THRILLER,0.00);
                        }
                        else if(g == Context.GENRE.ANIMATION){
                            auxMap.put(Context.GENRE.ANIMATION,0.00);
                        }
                        else if(g == Context.GENRE.SCIFI){
                            auxMap.put(Context.GENRE.SCIFI,0.00);
                        }
                        break;
                }
                if(!auxMap.isEmpty())
                    genreWeight.put(e,auxMap);
            }
        }
    }
    public static void printGenreWeight() {
        System.out.println(genreWeight.toString());
    }
    public static double getGenreWeighting(Context.EMOTION e, Context.GENRE g){ return genreWeight.get(e).get(g); }
    public static HashMap<Context.GENRE, Double> getMapForEmotion(Context.EMOTION e) { return genreWeight.get(e); }
}
