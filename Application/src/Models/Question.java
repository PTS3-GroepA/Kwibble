package Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dennis vermeulen on 20-03-17.
 */
public class Question {
    private String questionString;
    private int maxAnswerTime;
    private String source;
    private boolean showAlbumArt;
    private int score;
    private List <Question> questions;

    public Question(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score){
        this.questionString = questionString;
        this. maxAnswerTime = maxAnswerTime;
        this.source = source;
        this.showAlbumArt = showAlbumArt;
        this.score = score;
        this.questions = new ArrayList<Question>();
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
        this.source = source;
    }

    public boolean getShowAlbumArt(){
        return showAlbumArt;
    }

    public void setShowAlbumArt(boolean showAlbumArt){
        this.showAlbumArt = showAlbumArt;
    }






}
