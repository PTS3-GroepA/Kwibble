package Models.Answer;

/**
 * Created by dennisvermeulen on 20-03-17.
 *
 * Used for questions where the answer will be text.
 */
public class TextAnswer extends Answer<String> {

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
