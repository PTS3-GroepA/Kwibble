package Data.Context;

import Data.Context.Interfaces.Questionable;
import Models.Difficulty;
import Models.Question;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Max on 4/6/2017.
 */
public class QuestionMySQLContext implements Questionable {

    private Connection con;

    private void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi356103", "dbi356103", "Kwibble");
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
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
            e.printStackTrace();
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
                e.printStackTrace();
        }

        try {

            Question question = null;

            while(question == null) {

                // The random number will be the ID f the question to ask.
                int randomNum = ThreadLocalRandom.current().nextInt(0, numberOfQuestions);

                Statement stmt = (Statement) con.createStatement();
                //TODO take variable
                ResultSet result = stmt.executeQuery("SELECT * FROM Question WHERE QuestionID =" + 0 + " AND Difficulty <= " + dif);
                while (result.next()) {
                    question = new Question(result.getInt(1) ,result.getString(2), result.getBoolean(3), result.getString(4));
                }
            }

            return question;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
