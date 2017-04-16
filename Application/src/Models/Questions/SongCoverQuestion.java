package Models.Questions;

/**
 * Created by dennisvermeulen on 10-04-17.
 */
public class SongCoverQuestion extends Question {

    public SongCoverQuestion(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score) {
        super(questionString, maxAnswerTime, source, showAlbumArt, score);
    }

    public SongCoverQuestion(int id, String questionString, boolean showAlbumArt, String answerType) {
        super(id, questionString, showAlbumArt, answerType);
    }

    @Override
    public void generateQuestion() {

    }
}
