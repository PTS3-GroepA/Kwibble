package data.repos;

import data.context.interfaces.Answerable;
import models.answer.Answer;

import java.util.List;

/**
 * Created by dennisvermeulen on 27-03-17.
 */
public class AnswerRepository {

    private Answerable answerable;

    public AnswerRepository(Answerable answerable) {
        this.answerable = answerable;
    }

    public List<Answer> getAllAnswers() {

        List<Answer> answers;
        answers = answerable.getAnswers();
        return answers;

    }

}