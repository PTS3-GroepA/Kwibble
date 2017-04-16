package Models.Questions;

/**
 * Created by dennisvermeulen on 10-04-17.
 */
public class GenreQuestion extends Question {

    public GenreQuestion(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score) {
        super(questionString, maxAnswerTime, source, showAlbumArt, score);
    }

    public GenreQuestion(int id, String questionString, boolean showAlbumArt, String answerType) {
        super(id, questionString, showAlbumArt, answerType);
    }

    @Override
    public void generateQuestion() {

    }
}
