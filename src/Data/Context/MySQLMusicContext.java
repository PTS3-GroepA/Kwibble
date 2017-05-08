package Data.Context;

import Models.Answer.*;
import Data.Context.Interfaces.Answerable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * This class is used for fetching and storing data to the database.
 * It depends on a context file to locate said data.
 */
public class MySQLMusicContext implements Answerable {


    private Connection connection;
    private static final Logger LOGGER = Logger.getLogger(MySQLMusicContext.class.getName() );
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
                //answers.add(x,new TextAnswer(rs.getString("QuestionSting"), rs.getBoolean("ShowAlbumArt"),rs.getString("AnswerType"), rs.getInt("Difficulty")));
                x++;
            }


            return answers;




        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }


        return null;



    }

}