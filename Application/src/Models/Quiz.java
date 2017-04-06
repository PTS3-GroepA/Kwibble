package Models;

import Data.Context.QuestionMySQLContext;
import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Data.Repos.QuestionRepository;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * Main logic class of the game.
 */
public class Quiz {

    private int amountOfQuestions;
    private Difficulty difficulty;
    private String playlistURI;
    private List<Question> questions;
    private MusicRepository musicRepo;
    private QuestionRepository questionRepo;

    public Quiz(int amountOfQuestions, Difficulty difficulty, String playlistId){
        this.amountOfQuestions = amountOfQuestions;
        this.difficulty = difficulty;
        this.playlistURI = playlistId;
        this.questions = new ArrayList<Question>();

        questionRepo = new QuestionRepository(new QuestionMySQLContext());
        musicRepo = new MusicRepository(new SpotifyContext());
    }

    public Question generateQuestion(){
        return null;
    }

    public String getPlaylistURI(){
        return playlistURI;
    }

    public void setPlaylistURI(int PlayListId){
        this.playlistURI = playlistURI;

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


    /**
     * Play a media file from an url
     *
     * @param URL The spotify 30 second preview URI
     */
    private static void play(String URL) {
        System.out.println("Playing song");
        Media m = new Media(URL);
        MediaPlayer mp = new MediaPlayer(m);
        mp.play();
    }

}
