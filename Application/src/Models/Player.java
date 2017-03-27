package Models;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class Player {

    private int id;
    private String name;
    private int score;
    private String email;



    public Player(int id, String name, int score, String email){
        this.id = id;
        this.name = name;
        this.score = score;
        this.email = email;
    }

    public void createGameRoom(String name){
        //TODO
    }

}
