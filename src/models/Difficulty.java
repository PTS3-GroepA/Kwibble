package models;

/**
 * Created by dennisvermeulen on 20-03-17.
 * <p>
 * Question templates have a difficulty in the database.
 * When a difficulty is selected all questions with the same or lower difficulty will be available for playing.
 */
public enum Difficulty {
    EASY,
    NORMAL,
    HARD
}
