package Models;

import Models.Answer.Answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis vermeulen on 20-03-17.
 */
public class Question {
    private int questionID;
    private String questionString;
    private int maxAnswerTime;
    private String source;
    private String answerType;
    private boolean showAlbumArt;
    private int score;
    private List<Answer> answers;

    public Question(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score){
        this.questionString = questionString;
        this. maxAnswerTime = maxAnswerTime;
        this.source = source;
        this.showAlbumArt = showAlbumArt;
        this.score = score;
        this.answers = new ArrayList<>();
    }

    public Question(int id, String questionString, boolean showAlbumArt, String answerType) {
        this.questionID = id;
        this.questionString = questionString;
        this.showAlbumArt = showAlbumArt;
        this.answerType = answerType;
        this.answers = new ArrayList<>();
    }

    public int AnswerQuestion(){
        return 0;
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

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    @Override
    public String toString() {
        return "Question{" +
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
