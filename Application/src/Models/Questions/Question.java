package Models.Questions;

import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Models.Answer.Answer;
import com.wrapper.spotify.Api;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis vermeulen on 20-03-17.
 */
public abstract class Question {
    int questionID;
    String questionString;
    int maxAnswerTime;
    String source;
    String answerType;
    boolean showAlbumArt;
    int score;
    List<Answer> answers;
    MusicRepository musicRepo;
    public Question(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score){
        this.questionString = questionString;
        this. maxAnswerTime = maxAnswerTime;
        this.source = source;
        this.showAlbumArt = showAlbumArt;
        this.score = score;
        this.answers = new ArrayList<>();
        musicRepo = new MusicRepository(new SpotifyContext());
    }

    public Question(int id, String questionString, boolean showAlbumArt, String answerType) {
        this.questionID = id;
        this.questionString = questionString;
        this.showAlbumArt = showAlbumArt;
        this.answerType = answerType;
        this.answers = new ArrayList<>();
        musicRepo = new MusicRepository(new SpotifyContext());
        score =0;
    }

    public boolean answerQuestion(String answer){
        for(Answer a : answers) {
            if(a.ShowAnswer().equals(answer) && a.getIsCorrectAnswer()) {
                score = 1;
                return true;
            }
        }
        return false;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int GetScore(){
        return score;
    }

    public String getQuestionString(){
        return questionString;
    }

    public void setQuestionString(String questionString){
        this.questionString = questionString;
    }

    public int getMaxAnswerTime(){
        return maxAnswerTime;
    }

    public String getSource(){
        return source;
    }

    public void setSource(String source){
        this.source = source.substring(14);
    }

    public boolean getShowAlbumArt(){
        return showAlbumArt;
    }

    public void setShowAlbumArt(boolean showAlbumArt){
        this.showAlbumArt = showAlbumArt;
    }


    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public String getCorrectAnswer() {
        for(Answer a : answers) {
            if(a.getIsCorrectAnswer()) {
                return a.ShowAnswer();
            }
        }
        return null;
    }

    public String getAnswerString(int answerNumber) {
        return answers.get(answerNumber).ShowAnswer();
    }

    public abstract void generateAnswers();

    public void setApi(Api api) {
        musicRepo.setApi(api);
    }

    public String getPreviewURL() {
        return musicRepo.getTrack(source).getPreviewUrl();
    }

    @Override
    public String toString() {
        return "Questions{" +
                "questionString='" + questionString + '\'' +
                ", maxAnswerTime=" + maxAnswerTime +
                ", source='" + source + '\'' +
                ", answerType='" + answerType + '\'' +
                ", showAlbumArt=" + showAlbumArt +
                ", score=" + score +
                ", answers=" + answers.toString() +
                '}';
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }
}
