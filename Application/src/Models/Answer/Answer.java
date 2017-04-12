package Models.Answer;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public abstract class Answer {

    protected String name;
    protected boolean isCorrectAnswer;


    public Answer(String name, boolean isCorrectAnswer){
        this.name = name;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    public abstract String ShowAnswer();

    public boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

}
