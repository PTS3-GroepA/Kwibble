package data.context;

import data.repos.QuestionRepository;
import models.Difficulty;
import models.questions.Question;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Max on 15-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
class QuestionFileContextTest {
    @Test
    void getRandomQuestion() {
        QuestionRepository  repo = new QuestionRepository(new QuestionFileContext());
        Question question = repo.getRandomQuestion(Difficulty.EASY);
        System.out.println(question);
    }

    @Test
    void getAllQuestions() {
        QuestionRepository  repo = new QuestionRepository(new QuestionFileContext());
        ArrayList<Question> questions = new ArrayList<>();
        questions = (ArrayList<Question>) repo.getAllQuestions();

        for(Question e : questions) {
            System.out.println(e.toString());
        }
    }

}