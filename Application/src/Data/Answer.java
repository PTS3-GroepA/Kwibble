package Data;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public abstract  class Answer {

    protected String name;
    protected boolean showAlbumArt;
    protected String answerType;
    protected int difficulty;


    public Answer(String name, boolean showAlbumArt, String answerType, int difficulty){
        this.name = name;
        this.showAlbumArt = showAlbumArt;
        this.answerType = answerType;
        this.difficulty = difficulty;

    }

    public abstract boolean IsCorrectAnswer();
    public abstract void ShowAnswer();

}
