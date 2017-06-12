package models.questions;

import data.context.QuestionMSSQLContext;
import data.context.QuestionMySQLContext;
import data.repos.QuestionRepository;
import models.Difficulty;
import models.answer.Answer;
import models.answer.TextAnswer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dennisvermeulen on 16-04-17.
 */
class QuestionTest {

    Question question;
    QuestionRepository questionRepo;

    @BeforeEach
    void init() {

        questionRepo = new QuestionRepository(new QuestionMSSQLContext());
        question = questionRepo.getRandomQuestion(Difficulty.EASY);

    }


    @Test
    void testAnswerQuestion() {

        List<Answer> answerList = new ArrayList<>();
        answerList.add(new TextAnswer("TestAntwoord", true));
        answerList.add(new TextAnswer("TweedeAntwoord", false));

        assertEquals(true, true);


    }


    @Test
    void testGetCorrectAnswer() {

        question.addAnswer(new TextAnswer(("Antwoord"), true));

        String answer = question.getCorrectAnswer();

        assertEquals("Antwoord", answer);


    }

    @Test
    void testGetAnswerString() {

        List<Answer> answerList = new ArrayList<>();
        answerList.add(new TextAnswer("TestAntwoord", true));
        answerList.add(new TextAnswer("TweedeAntwoord", false));
        answerList.add(new TextAnswer("Drie", true));
        answerList.add(new TextAnswer("Vier", false));

        String answer = question.getAnswerString(1);
        String answerTwo = question.getAnswerString(3);

        assertEquals("TweedeAntwoord", answer);
        assertEquals("Vier", answerTwo);
    }

    @Test
    void testGenerateAnswersArtistNameQuestion() {
        Question qu = questionRepo.getRandomQuestion(Difficulty.EASY);
        qu.generateAnswers();
        question.getAnswerString(0);
        question.getAnswerString(1);
        question.getAnswerString(2);
        question.getAnswerString(3);

        assertEquals(true, question.getAnswerString(0));
        assertEquals(true, question.getAnswerString(1));
        assertEquals(true, question.getAnswerString(2));
        assertEquals(true, question.getAnswerString(3));
    }


}