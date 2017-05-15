package models.answer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dande on 15-4-2017.
 */
class TextAnswerTest {
    TextAnswer ta = new TextAnswer("TestAntwoord", true);

    @Test
    void showAnswer() {
        assertEquals(ta.ShowAnswer(), "TestAntwoord");
    }

    @Test
    void testToString() {
        assertEquals("TextAnswer{name='TestAntwoord'IsCorrect='true'}", ta.toString());
    }

}