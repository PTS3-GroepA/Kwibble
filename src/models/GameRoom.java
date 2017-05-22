package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.*;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * This class will contain all players in a multi-player scenario.
 */
public class GameRoom {

    private String sessionName;
    private Quiz quiz;
    private Map<Player, Boolean> players;
    ObservableMap<Player, Boolean> observablePlayer;


    public GameRoom(Player owner, String sessionName) {
        players = new HashMap<>();
        players.put(owner, true);
        this.sessionName = sessionName;
        observablePlayer = FXCollections.observableMap(players);
    }


    public void join(Player player) {
        players.put(player,false);
    }

    public void leave(Player player) {
        players.remove(player);
    }

    public void generateQuestions() {
        quiz.generateQuestions();
    }

    public void setDifficulty(Difficulty dif) {
        quiz.setDifficulty(dif);
    }

    public ObservableMap<Player, Boolean> getPlayers() {return observablePlayer; }

    public void addQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
