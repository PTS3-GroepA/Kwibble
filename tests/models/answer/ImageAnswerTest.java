package models.answer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dande on 15-4-2017.
 */
class ImageAnswerTest {
    ImageAnswer ia = new ImageAnswer("testanswer", true);

    @Test
    void showAnswer() {
        assertEquals(null, ia.ShowAnswer());
    }

}