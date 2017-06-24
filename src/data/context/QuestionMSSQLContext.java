package data.context;

import data.context.interfaces.Questionable;
import models.Difficulty;
import models.questions.ArtistNameQuestion;
import models.questions.Question;


import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 29/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class QuestionMSSQLContext implements Questionable {
    private static final Logger LOGGER = Logger.getLogger(QuestionMSSQLContext.class.getName());
    private String connectionUrl = "jdbc:sqlserver://kwibblesonar.database.windows.net:1433;database=kwibble;user=kwibble@kwibblesonar;password=Wachtwoord1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection con = null;
    private Statement stmt = null;

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
        int dif = difficulty.ordinal() + 1;

        // TODO take random number
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);

            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM question WHERE QuestionID =" + 1 + " AND Difficulty <= " + dif);

            while (rs.next()) {
                String name = rs.getString(4);
                name = name.replaceAll("\\s+","");
                switch (name) {
                    case "ARTNAME":
                        return new ArtistNameQuestion(rs.getString(2), rs.getBoolean(3), rs.getString(4), Difficulty.values()[rs.getInt(5)], 10);
                    default :
                        System.out.println("Wrong question type");
                        break;
                }
            }
        } catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return null;
    }
}
