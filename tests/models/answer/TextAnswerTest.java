package models.answer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dande on 15-4-2017.
 */
class TextAnswerTest {

    TextAnswer ta;

    @BeforeEach
    void prepare() {
        ta = new TextAnswer("TestAntwoord", true);
    }

    @Test
    void showAnswer() {
        assertEquals(ta.ShowAnswer(), "TestAntwoord");
    }


}