package Models;

import javax.print.DocFlavor;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class Player {

    private int id;
    private String name;
    private int score;
    private String email;
    private String password;



    public Player(int id, String name, int score, String email){
        this.id = id;
        this.name = name;
        this.score = score;
        this.email = email;
    }

    public void createGameRoom(String name){
        //TODO
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
