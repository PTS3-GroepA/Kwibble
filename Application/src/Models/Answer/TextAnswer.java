package Models.Answer;

/**
 * Created by dennisvermeulen on 20-03-17.
 */
public class TextAnswer extends Answer {

    public TextAnswer(String name, boolean isCorrectAnswer) {
        super(name, isCorrectAnswer);
    }

    @Override
    public String ShowAnswer() {
        return name;
    }

    @Override
    public String toString() {
        return "TextAnswer{" +
                "name='" + name + '\'' +
                "IsCorrect='" + isCorrectAnswer + '\'' +
                '}';
    }
}
