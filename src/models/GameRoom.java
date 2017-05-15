package models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * This class will contain all players in a multi-player scenario.
 */
public class GameRoom {

    private Player owner;
    private String sessionName;
    private Quiz quiz;
    private List<Player> players;


    public GameRoom(Player owner, String sessionName, Quiz quiz) {
        this.owner = owner;
        this.sessionName = sessionName;
        this.quiz = quiz;
        this.players = new ArrayList<Player>();
    }

    public void Join(Player player) {
        players.add(player);
    }

    public void Leave(Player player) {
        players.remove(player);
    }

    public void generateQuestions() {
        quiz.generateQuestions();
    }

    public void setDifficulty(Difficulty dif) {
        quiz.setDifficulty(dif);
    }
}
