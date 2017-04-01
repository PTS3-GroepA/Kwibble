package Data.Repos;

import Data.Answer;
import Data.Context.Interfaces.Answerable;
import Logic.Question;

import java.util.List;

/**
 * Created by dennisvermeulen on 27-03-17.
 */
public class AnswerRepository {

    private Answerable answerable;

    public AnswerRepository(Answerable answerable){
        this.answerable = answerable;
    }

    public List<Answer> getAllAnswers(){

        List<Answer> answers;
        answers = answerable.getAnswers();
        return answers;

    }

}
