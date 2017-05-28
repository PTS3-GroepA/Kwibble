package models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.io.Serializable;
import java.util.*;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * This class will contain all players in a multi-player scenario.
 */
public class GameRoom implements Serializable {

    private String sessionName;
    private Quiz quiz;
    private Map<Player, Boolean> players;


    public GameRoom(Player owner, String sessionName) {
        players = new HashMap<>();
        players.put(owner, true);
        this.sessionName = sessionName;
    }

    public GameRoom(String sessionName) {
        players = new HashMap<>();
        this.sessionName = sessionName;
    }


    public void join(Player player) {
        players.put(player,false);
        System.out.println(players);
    }

    public void leave(Player player) {
        System.out.println("Removing player: " + player);
        players.entrySet().removeIf(entry -> Objects.equals(entry.getKey().getName(), player.getName()));
        System.out.println(players);
    }

    public void generateQuestions() {
        quiz.generateQuestions();
    }

    public void setDifficulty(Difficulty dif) {
        quiz.setDifficulty(dif);
    }

    public Map<Player, Boolean> getPlayers() {return players; }

    public void addQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getName() {
        return sessionName;
    }
}
