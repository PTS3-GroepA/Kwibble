package models;

import data.context.QuestionMSSQLContext;
import data.context.SpotifyContext;
import data.repos.MusicRepository;
import data.repos.QuestionRepository;
import gui.controller.GameBrowserController;
import gui.controller.GameRoomController;
import models.questions.Question;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennisvermeulen on 20-03-17.
 * <p>
 * Main logic class of the game.
 */
public class Quiz implements Serializable{

    private int amountOfQuestions;
    private Difficulty difficulty;
    private String userID;
    private String playlistURI;
    private List<Question> questions;
    private MusicRepository musicRepo;
    private QuestionRepository questionRepo;
    private SimpleServer server;
    private GameRoomController gameRoomController;
    private int questionsPlayed = 0;

    public Quiz(int amountOfQuestions, Difficulty difficulty, String userID, String playlistId) {
        this.amountOfQuestions = amountOfQuestions;
        this.difficulty = difficulty;
        this.userID = userID;
        this.playlistURI = playlistId;
        this.questions = new ArrayList<>();

        SpotifyContext context = new SpotifyContext();

        questionRepo = new QuestionRepository(new QuestionMSSQLContext());
        musicRepo = new MusicRepository(context);
        server = new SimpleServer(context, this);
    }

    public void setGameBrowserController(GameRoomController controller){
        this.gameRoomController = controller;
    }

    /**
     * Create a question based on the playlist and the difficulty level
     *
     */
    public void generateQuestions() {
        //todo zorg dat de lijst vragen cleared na dat er gespeeld is
        questions.clear();
        System.out.println("Generating questions");
        for (int i = 1; i <= amountOfQuestions; i++) {

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
        }
    }

    /**
     * The URI of the spotify playlist to base all questions on.
     *
     * @return Returns the playlist URI.
     */
    String getPlaylistURI() {
        return playlistURI;
    }

    void setPlaylistURI(String playListURI) {
        this.playlistURI = playListURI;
    }

    /**
     * The exact amount of questions the user wants to play.
     *
     * @return Returns the amount of questions the quiz will have
     */
    int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    void setAmountOfQuestions(int amountOfQuestions) {
        this.amountOfQuestions = amountOfQuestions;
    }

    /**
     * The difficulty level that the user wants to play.
     * questions generation will be based on this difficulty level.
     *
     * @return The difficulty of the quiz
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Get a question at a specific location in the list.
     *
     * @param questionNumber The zero-based index location.
     * @return The question at the given location.
     */
    public Question getQuestion(int questionNumber) {
        return questions.get(questionNumber);
    }

    /**
     * Return all questions in the quiz.
     *
     * @return A list of all questions in the quiz.
     */
    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    /**
     * Check whether the user is logged in on Spotify.
     *
     * @return Returns true when the user is logged in.
     */
    public boolean checkAuthorization() {
        return musicRepo.checkAuthorization();
    }

    /**
     * gets the URL which the user can visit to authorise the application via spotify.
     *
     * @return The URL the user must visit to authorise.
     */
    public String getAuthenticationURL() {
        server.start();
        return musicRepo.getAuthenticationURL();
    }

    public void confirmAuthorisation() {
        server = null;
        generateQuestions();
        gameRoomController.confirmAuth();
    }

    public Question getQuestionToPlay() {
        return questions.get(questionsPlayed);
    }
}
