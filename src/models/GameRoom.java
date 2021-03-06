package models;

import java.io.Serializable;
import java.util.*;
import java.util.function.BiConsumer;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * This class will contain all players in a multi-player scenario.
 */
public class GameRoom implements Serializable {

    private String sessionName;
    public Quiz quiz;
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

    public void increaseScoreForPlayer(Player player , int score) {
        for (Map.Entry<Player, Boolean> entry : players.entrySet())
        {
            if(entry.getKey().getName().equals(player.getName())) {
                entry.getKey().addScore(score);
            }
        }
    }
}
