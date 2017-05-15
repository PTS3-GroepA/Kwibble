package models.answer;

/**
 * Created by dennisvermeulen on 20-03-17.
 * <p>
 * Super class for answers.
 * Every Question will contain 4 answers.
 * <p>
 * Because this uses generic types to make sure to cast whenever you call the showAnswer function.
 */
public abstract class Answer<T> {

    protected String name;
    protected boolean isCorrectAnswer;


    public Answer(String name, boolean isCorrectAnswer) {
        this.name = name;
        this.isCorrectAnswer = isCorrectAnswer;
    }

    /**
     * This will return the answer to display.
     * The actual object to return will be defined by the child class.
     * <p>
     * Remember to cast to prevent errors!
     *
     * @return The answer to display on the screen.
     */
    public abstract T ShowAnswer();

    /**
     * Shows whether this answer is the actual answer to the question.
     *
     * @return Shows if this answer is the actual answer to the question.
     */
    public boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

}
