package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class GameRoom {

    private Player owner;
    private String name;
    private Quiz quiz;
    private List <Player> players;


    public GameRoom(Player owner, String name, Quiz quiz){
        this.owner = owner;
        this.name = name;
        this.quiz = quiz;
        this.players = new ArrayList<Player>();
    }

    public void Join(Player player){

    }

    public void Leave(Player player){

    }

}
