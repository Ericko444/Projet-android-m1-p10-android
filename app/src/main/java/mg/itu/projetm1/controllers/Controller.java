package mg.itu.projetm1.controllers;

public final class Controller {
    private static Controller instance = null;
    private String BASE_URL = "https://9e39-197-149-60-3.ngrok-free.app/";

    private Controller(){
        super();
    }

    public static final Controller getInstance(){
        if (Controller.instance == null){
            Controller.instance = new Controller();
        }
        return instance;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}
