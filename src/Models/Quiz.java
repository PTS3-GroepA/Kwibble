package Models;

import Data.Context.QuestionMySQLContext;
import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Data.Repos.QuestionRepository;
import GUI.Controller.LocalGameController;
import Models.Questions.*;

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
    private String userID;
    private String playlistURI;
    private List<Question> questions;
    private MusicRepository musicRepo;
    private QuestionRepository questionRepo;
    private LocalGameController controller;
    private SimpleServer server;

    public Quiz(int amountOfQuestions, Difficulty difficulty,String userID, String playlistId, LocalGameController controller){
        this.amountOfQuestions = amountOfQuestions;
        this.difficulty = difficulty;
        this.userID = userID;
        this.playlistURI = playlistId;
        this.questions = new ArrayList<>();
        this.controller = controller;

        SpotifyContext context = new SpotifyContext();

        questionRepo = new QuestionRepository(new QuestionMySQLContext());
        musicRepo = new MusicRepository(context);
        server = new SimpleServer(context, this);

    }

    /**
     * Create a question based on the playlist and the difficulty level
     *
     * @return Returns a question that can be asked in the quiz.
     */
    public void generateQuestions(){
        //todo zorg dat de lijst vragen cleared na dat er gespeeld is

        System.out.println("Generating questions");
        for(int i = 1; i <= amountOfQuestions; i++) {

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
    public String getPlaylistURI(){
        return playlistURI;
    }

    public void setPlaylistURI(String playListURI){
        this.playlistURI = playListURI;
    }

    /**
     * The exact amount of questions the user wants to play.
     *
     * @return Returns the amount of questions the quiz will have
     */
    public int getAmountOfQuestions(){
        return amountOfQuestions;
    }

    public void setAmountOfQuestions(int amountOfQuestions){
        this.amountOfQuestions = amountOfQuestions;
    }

    /**
     * The difficulty level that the user wants to play.
     * Questions generation will be based on this difficulty level.
     *
     * @return The difficulty of the quiz
     */
    public Difficulty getDifficulty(){
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty){
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

    /**
     * Method called when the server has received the access token from spotify.
     */
    public void confirmAuthorisation() {
        server = null;
        controller.confirmAuthorization();
    }

}
