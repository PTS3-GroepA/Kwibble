package Data.Context.Interfaces;

import Models.Difficulty;
import Models.Questions.*;

import java.util.List;

/**
 * Created by Max Meijer on 03/04/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 *
 * Interface for question related database usage.
 */
public interface Questionable {

    List<Question> getAllQuestions();

    Question getQuestionById(int id);

    Question getRandomQuestion(Difficulty difficulty);
}