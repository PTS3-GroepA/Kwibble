package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class Quiz {

    private int amountOfQuestions;
    private Difficulty difficulty;
    private int playlistId;
    private List<Question> questions;


    public Quiz(int amountOfQuestions, Difficulty difficulty, int playlistId){
        this.amountOfQuestions = amountOfQuestions;
        this.difficulty = difficulty;
        this.playlistId = playlistId;
        this.questions = new ArrayList<Question>();
    }

    public Question generateQuestion(){
        return null;
    }

    public int getPlaylistId(){
        return playlistId;
    }

    public void setPlaylistId(int PlayListId){
        this.playlistId = playlistId;

    }

    public int getAmountOfQuestions(){
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(int amountOfQuestions){
        this.amountOfQuestions = amountOfQuestions;
    }

    public Difficulty getDifficulty(){
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty){
        this.difficulty = difficulty;
    }

}
