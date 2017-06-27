package models.questions;

import models.Difficulty;
import models.Player;
import models.answer.Answer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Max on 30-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * Question class used for sending to players in a multiplayer scenario.
 */
public class SerQuestion implements Serializable {
    private String questionString;
    private int maxAnswerTime;
    private String previewUrl;
    private boolean showAlbumArt;
    private int score;
    private List<Answer> answers;

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    private Player player;

    public SerQuestion(String questionString, int maxAnswerTime, String previewUrl, boolean showAlbumArt, int score, List<Answer> answers) {
        this.questionString = questionString;
        this.maxAnswerTime = maxAnswerTime;
        this.previewUrl = previewUrl;
        this.showAlbumArt = showAlbumArt;
        this.score = score;
        this.answers = answers;
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
     * Returns the 30 second preview URL for the track.
     * This can be played directly from a media player.
     *
     * @return the URL to the preview.
     */
    public String getPreviewURL() {
        return previewUrl;
    }

    public String getQuestionString() {
        return questionString;
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

    public int getScore() {
        return score;
    }

    public int getMaxAnswerTime() {
        return maxAnswerTime;
    }

    public void calculateScore(double percentage) {
        score = (int) ( 100 - (percentage * 100));
    }
}
