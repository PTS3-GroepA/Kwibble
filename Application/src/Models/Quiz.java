package Models;

import Data.Context.QuestionMySQLContext;
import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Data.Repos.QuestionRepository;
import Models.Answer.TextAnswer;
import Models.Question.Question;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

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

    public Quiz(int amountOfQuestions, Difficulty difficulty,String userID, String playlistId){
        this.amountOfQuestions = amountOfQuestions;
        this.difficulty = difficulty;
        this.userID = userID;
        this.playlistURI = playlistId;
        this.questions = new ArrayList<>();

        questionRepo = new QuestionRepository(new QuestionMySQLContext());
        musicRepo = new MusicRepository(new SpotifyContext());

    }

    /**
     * Create a question based on the playlist and the difficulty level
     *
     * @return Returns a question that can be asked in the quiz.
     */
    public void generateQuestions(){

        for(int i = 1; i <= amountOfQuestions; i++) {

            // Get a base random question from the database.
            Question question = questionRepo.getRandomQuestion(difficulty);

            // Add a random song from the playlist as source.
            question.setSource(musicRepo.getRandomTrackFromPlaylist(userID, playlistURI).getUri());

            System.out.println(question);

            // Partially hard code question generator for the sake of the demo
            // We'll have to find a solution to this in the near future
            // TODO
            if (question.getQuestionID() == 0) {

                Track track = musicRepo.getTrack(question.getSource());
                // Add the correct answer.
                SimpleArtist artist = track.getArtists().get(0);
                question.addAnswer(new TextAnswer(artist.getName(), true));

                // Add 3 related artist as fake answers
                List<Artist> related = musicRepo.getRelatedArtist(artist.getId());
                for (int j = 0; j < 3; j++) {
                    int randomNum = ThreadLocalRandom.current().nextInt(0, related.size());
                    question.addAnswer(new TextAnswer(related.get(randomNum).getName(), false));
                }

                System.out.println(question);
                questions.add(question);
            }
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
     * Question generation will be based on this difficulty level.
     *
     * @return The difficulty of the quiz
     */
    public Difficulty getDifficulty(){
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty){
        this.difficulty = difficulty;
    }


    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void authenticate() {
        System.out.println(musicRepo.getAuthenticationURL());
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
