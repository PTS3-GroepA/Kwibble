package models;

import gui.controller.LocalGameController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by dande on 15-4-2017.
 */
class QuizTest {
    Quiz q = new Quiz(3, Difficulty.EASY, "spotify:user:118534594", "spotify:user:spotify:playlist:37i9dQZF1DX1OY2Lp0bIPp");

    @Test
    void generateQuestions() {
        //todo lokale databronnen shcrijven om te kunnen testen
    }


    @Test
    void getPlaylistURI() {
        assertEquals("spotify:user:spotify:playlist:37i9dQZF1DX1OY2Lp0bIPp", q.getPlaylistURI());
    }

    @Test
    void setPlaylistURI() {
        q.setPlaylistURI("testUri");
        assertEquals("testUri", q.getPlaylistURI());
    }

    @Test
    void getAmountOfQuestions() {
        assertEquals(3, q.getAmountOfQuestions());
    }

    @Test
    void setAmountOfQuestions() {
        q.setAmountOfQuestions(5);
        assertEquals(5, q.getAmountOfQuestions());
    }

    @Test
    void getDifficulty() {
        assertEquals(Difficulty.EASY, q.getDifficulty());
    }

    @Test
    void setDifficulty() {
        q.setDifficulty(Difficulty.HARD);
        assertEquals(Difficulty.HARD, q.getDifficulty());
    }

    @Test
    void getQuestion() {
        //todo Make local repo for testing
    }

    @Test
    void getQuestions() {
        //todo Make local repo for testing
    }

    @Test
    void setQuestions() {
        //todo Make local repo for testing
    }

    @Test
    void checkAuthorization() {
        //todo Make local repo for testing
    }

    @Test
    void getAuthenticationURL() {
        //todo Make local repo for testing
    }

    @Test
    void confirmAuthorisation() {
        //todo Make local repo for testing
    }

}