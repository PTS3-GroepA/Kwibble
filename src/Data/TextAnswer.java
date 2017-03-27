package Data;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class TextAnswer extends Answer {


    public TextAnswer(String name, boolean showAlbumArt, String answerType, int difficulty) {
        super(name, showAlbumArt, answerType, difficulty);
    }

    @Override
    public boolean IsCorrectAnswer() {
        return false;
    }

    @Override
    public void ShowAnswer() {

    }
}
