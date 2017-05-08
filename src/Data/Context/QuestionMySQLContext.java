package Data.Context;

import Data.Context.Interfaces.Questionable;
import Models.Difficulty;
import Models.Questions.*;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 03/04/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 *
 * Provides communication via an MySQl database.
 */
public class QuestionMySQLContext implements Questionable {

    private static final Logger LOGGER = Logger.getLogger( QuestionMySQLContext.class.getName());
    private Connection con;

    private void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi356103", "dbi356103", "Kwibble");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    public QuestionMySQLContext() {
        initConnection();
    }

    @Override
    public List<Question> getAllQuestions() {
        return null;
    }

    @Override
    public Question getQuestionById(int id) {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Question WHERE QuestionID =" + id);
            while(result.next()) {
                System.out.println(result.getString(1) +  result.getString(2) + result.getString(3) + result.getString(4) +result.getString(5));
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }

    /**
     * Select a random question to aks from the database based on the difficulty level.
     *
     * @param difficulty The difficulty of the quiz. This will include question with the difficulty under itself as well.
     * @return Return a base question.
     */
    @Override
    public Question getRandomQuestion(Difficulty difficulty) {
        int numberOfQuestions = -1;
        int dif = difficulty.ordinal() + 1;

        try {

            // Get the number of question in the database to base a random number on.
            Statement getNumberOfquestions = (Statement) con.createStatement();
            ResultSet result = getNumberOfquestions.executeQuery("SELECT COUNT(*) FROM Question");
            while (result.next()) {
                numberOfQuestions = result.getInt(1);
                System.out.println(numberOfQuestions);
            }
        }
        catch(SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        try {
                // The random number will be the ID of the question to ask.
                int randomNum = ThreadLocalRandom.current().nextInt(0, numberOfQuestions);
                Statement stmt = (Statement) con.createStatement();

                // TODO take variable
                // TODO Extend switch case to take all values
                ResultSet result = stmt.executeQuery("SELECT * FROM Question WHERE QuestionID =" + 0 + " AND Difficulty <= " + dif);
                while (result.next()) {
                    switch (result.getString(4)) {
                        case "ARTNAME":
                            return new ArtistNameQuestion(result.getString(2), result.getBoolean(3), result.getString(4));
                    }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

        return null;
    }
}
