package Data.Repos;

import Models.Answer.Answer;
import Data.Context.Interfaces.Answerable;
import Models.Answer.TextAnswer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * This class is used for fetching and storing data to the database.
 * It depends on a context file to locate said data.
 */
public class MySQLMusicContext implements Answerable {


    private Connection connection;

    @Override
    public List<Answer> getAnswers() {
        try{
            List<Answer> answers = new ArrayList<Answer>();


            connection = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi344291", "dbi356103", "Kwibble");
            Statement stmt = connection.createStatement();
            ResultSet rs =  stmt.executeQuery("SELECT QuestionID, QuestionString, ShowAlbumArt, AnswerType, Difficulty FROM question");

            int x = 0;
            while(rs.next())
            {
                answers.add(x,new TextAnswer(rs.getString("QuestionSting"), rs.getBoolean("ShowAlbumArt"),rs.getString("AnswerType"), rs.getInt("Difficulty")));
                x++;
            }


            return answers;




        } catch (SQLException e1) {
            e1.printStackTrace();
        }


        return null;



    }

}
