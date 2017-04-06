package Models.Answer;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class TextAnswer extends Answer {


    private final String name;
    private final boolean showAlbumArt;
    private final String answerType;
    private final int difficulty;

    public TextAnswer(String name, boolean showAlbumArt, String answerType, int difficulty) {
        super(name, showAlbumArt, answerType, difficulty);
        this.name = name;
        this.showAlbumArt = showAlbumArt;
        this.answerType = answerType;
        this.difficulty = difficulty;
    }

    @Override
    public boolean IsCorrectAnswer() {
        return false;
    }

    @Override
    public String ShowAnswer() {
        return name;
    }
}
