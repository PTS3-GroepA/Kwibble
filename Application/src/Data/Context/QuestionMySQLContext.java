package Data.Context;

import Data.Context.Interfaces.Questionable;
import Models.Question;
import com.mysql.jdbc.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

    @Override
    public Question getRandomQuestion() {
        try {
            Statement stmt = (Statement) con.createStatement();
            ResultSet result = stmt.executeQuery("SELECT * FROM Question WHERE QuestionID =" + 1);
            while(result.next()) {
                System.out.println(result.getString(1) +  result.getString(2) + result.getString(3) + result.getString(4) +result.getString(5));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
