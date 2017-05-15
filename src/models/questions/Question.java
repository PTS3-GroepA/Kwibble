package models.questions;

import com.wrapper.spotify.Api;
import data.context.SpotifyContext;
import data.repos.MusicRepository;
import models.answer.Answer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis vermeulen on 20-03-17.
 * <p>
 * The question class will contain all information to play one single question in the quiz.
 */
public abstract class Question implements Serializable {
    String questionString;
    int maxAnswerTime;
    String source;
    String answerType;
    boolean showAlbumArt;
    int score;
    List<Answer> answers;
    MusicRepository musicRepo;

    public Question(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score) {
        this.questionString = questionString;
        this.maxAnswerTime = maxAnswerTime;
        this.source = source;
        this.showAlbumArt = showAlbumArt;
        this.score = score;
        this.answers = new ArrayList<>();
        musicRepo = new MusicRepository(new SpotifyContext());
    }

    public Question(String questionString, boolean showAlbumArt, String answerType) {
        this.questionString = questionString;
        this.showAlbumArt = showAlbumArt;
        this.answerType = answerType;
        this.answers = new ArrayList<>();
        musicRepo = new MusicRepository(new SpotifyContext());
        score = 0;
    }

    public boolean answerQuestion(String answer) {
        for (Answer a : answers) {
            if (a.ShowAnswer().equals(answer) && a.getIsCorrectAnswer()) {
                score = 1;
                return true;
            }
        }
        return false;
    }

    public void setScore(int score) {
        this.score = score;
    }

    /**
     * The amount of points that was gained while playing this question.
     *
     * @return The score gained by playing this question.
     */
    public int GetScore() {
        return score;
    }

    /**
     * The question string that can be prompted to the user.
     *
     * @return The question to display to the user.
     */
    public String getQuestionString() {
        return questionString;
    }

    public void setQuestionString(String questionString) {
        this.questionString = questionString;
    }

    /**
     * The amount of time the user has to play this question.
     *
     * @return The maximum answer time for this question.
     */
    public int getMaxAnswerTime() {
        return maxAnswerTime;
    }

    /**
     * The Spotify URI of the track on which this question is based.
     *
     * @return The URI of the track.
     */
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source.substring(14);
    }

    /**
     * Shows whether or not the album cover can be displayed during the question.
     * This is defined in the database and makes sure the album cover does not give away the answer to the question.
     *
     * @return Whether or not to display the album cover.
     */
    public boolean getShowAlbumArt() {
        return showAlbumArt;
    }

    public void setShowAlbumArt(boolean showAlbumArt) {
        this.showAlbumArt = showAlbumArt;
    }

    /**
     * The type of answer that is required for this question.
     * Based on this property the child class can generate the right answers.
     *
     * @return The type of answers required.
     */
    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    void addAnswer(Answer answer) {
        answers.add(answer);
    }

    /**
     * Gets the correct answer from the four possible answers.
     *
     * @return The correct answer.
     */
    public String getCorrectAnswer() {
        for (Answer a : answers) {
            if (a.getIsCorrectAnswer()) {
                return (String) a.ShowAnswer();
            }
        }
        return null;
    }

    /**
     * Gets the answer at a specific location from the answer list.
     *
     * @param answerNumber The zero-index location of the answer in the answer list.
     * @return The answer at the specific location.
     */
    public String getAnswerString(int answerNumber) {
        return (String) answers.get(answerNumber).ShowAnswer();
    }

    /**
     * Method for generating questions.
     * Child classes implement this method based on what answer type they have.
     */
    public abstract void generateAnswers();

    /**
     * Set the API of the spotify context.
     * This allows credentials to be passed around internally without comprising data or requiring the user to login again.
     *
     * @param api The new api.
     */
    public void setApi(Api api) {
        musicRepo.setApi(api);
    }

    /**
     * Returns the 30 second preview URL for the track.
     * This can be played directly from a media player.
     *
     * @return the URL to the preview.
     */
    public String getPreviewURL() {
        return musicRepo.getTrack(source).getPreviewUrl();
    }

    @Override
    public String toString() {
        return "questions{" +
                "questionString='" + questionString + '\'' +
                ", maxAnswerTime=" + maxAnswerTime +
                ", source='" + source + '\'' +
                ", answerType='" + answerType + '\'' +
                ", showAlbumArt=" + showAlbumArt +
                ", score=" + score +
                ", answers=" + answers.toString() +
                '}';
    }
}
