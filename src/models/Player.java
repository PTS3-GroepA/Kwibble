package models;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class Player {

    private int id;
    private String name;
    private int score;
    private String email;
    private String password;

    public Player(String name, int score, String email, String password) {
        this.name = name;
        this.score = score;
        this.email = email;
        this.password = password;
    }

    public Player(int id, String name, int score, String email) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.email = email;
    }

    public Player(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public void createGameRoom(String name) {
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

<<<<<<< HEAD
    @Override
    public String toString() {
        return name;
    }
=======


>>>>>>> 6be990da0226fa075ef1d6b5c2b4e31c3e5b4a82
}
