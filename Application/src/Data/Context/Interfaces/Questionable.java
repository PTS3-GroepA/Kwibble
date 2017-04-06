package Data.Context.Interfaces;

import Models.Question;

import java.util.List;

/**
 * Created by Max on 4/6/2017.
 */
public interface Questionable {

    List<Question> getAllQuestions();

    Question getQuestionById(int id);

    Question getRandomQuestion();
}
