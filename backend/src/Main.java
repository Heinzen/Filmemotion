public class Main {
    public static void main(String[] args) {
        init();
    }

    private static void init(){
        SocketEndpoint s = new SocketEndpoint(4444);
        s.start();
        new Context("resources/moviedata.json");
        new MovieFactory();
    }
}
