package Models.Question;

/**
 * Created by dennisvermeulen on 10-04-17.
 */
public class LabelQuestion extends Question {

    public LabelQuestion(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score) {
        super(questionString, maxAnswerTime, source, showAlbumArt, score);
    }

    public LabelQuestion(int id, String questionString, boolean showAlbumArt, String answerType) {
        super(id, questionString, showAlbumArt, answerType);
    }

    @Override
    public void generateQuestion() {

    }
}
