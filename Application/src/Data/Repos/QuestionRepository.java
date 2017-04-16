package Data.Repos;

import Data.Context.Interfaces.Questionable;
import Models.Difficulty;
import Models.Question.Question;

import java.util.List;

/**
 * Created by Max on 4/6/2017.
 */
public class QuestionRepository implements Questionable {

    private Questionable context;

    public QuestionRepository(Questionable context) {
        this.context = context;
    }

    @Override
    public List<Question> getAllQuestions() {
        return context.getAllQuestions();
    }

    @Override
    public Question getQuestionById(int id) {
        return context.getQuestionById(id);
    }

    @Override
    public Question getRandomQuestion(Difficulty difficulty) {
        return context.getRandomQuestion(difficulty);
    }
}
