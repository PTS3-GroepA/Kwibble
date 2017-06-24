package models;

import com.sun.javafx.tk.Toolkit;
import com.wrapper.spotify.Api;
import data.context.QuestionMSSQLContext;
import data.context.SpotifyContext;
import data.repos.MusicRepository;
import data.repos.QuestionRepository;
import javafx.concurrent.Task;
import models.questions.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max on 24-Jun-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class QuestionGenerator extends Task<List<Question>> {

    /**
     * The difficulty level of the questions.
     */
    private Difficulty difficulty;
    /**
     * The number of question to generate.
     */
    private int numberOfQuestions;
    private List<Question> questions;
    private QuestionRepository questionRepo;
    private MusicRepository musicRepo;
    private String userID;
    private String playlistURI;

    public QuestionGenerator(Difficulty dif, int numberOfQuestions, Api api, String userID, String playlistURI){
        this.difficulty = dif;
        this.numberOfQuestions = numberOfQuestions;
        this.userID = userID;
        this.playlistURI = playlistURI;

        questions = new ArrayList<>();
        questionRepo = new QuestionRepository(new QuestionMSSQLContext());
        musicRepo = new MusicRepository(new SpotifyContext());
        musicRepo.setApi(api);
    }

    @Override
    protected List<Question> call() throws Exception {
        for (int i = 1; i <= numberOfQuestions; i++) {

            // Get a base random question from the database.
            Question question = questionRepo.getRandomQuestion(difficulty);
            System.out.println(question);

            // Pass the api with credentials to the question.
            question.setApi(musicRepo.getApi());
            System.out.println(question);

            // Add a random song from the playlist as source.
            question.setSource(musicRepo.getRandomTrackFromPlaylist(userID, playlistURI).getUri());

            question.generateAnswers();
            System.out.println(question);
            questions.add(question);
            updateProgress(i, numberOfQuestions);
        }

        return questions;
    }
}
