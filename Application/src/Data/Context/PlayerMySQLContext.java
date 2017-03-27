package Data.Context;

import Data.Context.Interfaces.PlayerContextInterface;
import Models.Player;
import java.sql.*;
import java.io.*;



/**
 * Created by Bryan on 27-3-2017.
 */
public class PlayerMySQLContext implements PlayerContextInterface {

    private Connection con;

    private void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi344291", "dbi344291", "Kwibble");
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
        }
    }
    public PlayerMySQLContext() {

    }

    @Override
    public boolean AddUser(Player player) {
        try{
            initConnection();
            PreparedStatement myStatement = con.prepareStatement("INSERT INTO player (Email,Password,Salt,Score) VALUES ?, ?, ?,?");

            myStatement.setString(1,player.getEmail());
            myStatement.setString(2,player.getPassword());
            //Create salt here
            myStatement.setInt(4, player.getScore());
            myStatement.executeUpdate();

            con.close();

        }
        catch(Exception exc){

            return true;
        }
        return true;
    }

}
