package data.context;

import com.sun.javafx.scene.layout.region.Margins;
import data.context.interfaces.Questionable;
import models.Difficulty;
import models.questions.ArtistNameQuestion;
import models.questions.Question;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 15/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class QuestionFileContext implements Questionable {

    private static final Logger LOGGER = Logger.getLogger(QuestionFileContext.class.getName());
    @Override
    public List<Question> getAllQuestions() {
        return null;
    }

    @Override
    public Question getQuestionById(int id) {
        return null;
    }

    @Override
    public Question getRandomQuestion(Difficulty difficulty) {

        Scanner c = null;
        try {
            c = new Scanner(new BufferedReader(new FileReader("C:\\Users\\Max\\Source\\repos\\Semester 3\\Kwibble\\src\\questions\\0_1.txt")));
        } catch (FileNotFoundException e) {
            LOGGER.severe(e.getMessage());
        }

        try {
            assert c != null;
            while (c.hasNextLine()) {
                // TODO fix string splitting on whitespaces
                String[] result = c.next().split(",");
                for(String s :result) {
                    System.out.println(s);
                }
                switch (result[3]) {
                    case "ARTNAME" :
                        return new ArtistNameQuestion(result[1], Boolean.parseBoolean(result[2]), result[3], Difficulty.values()[Integer.parseInt(result[4])]);
                }
            }
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }
}
