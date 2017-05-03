package Data.Context;

import Data.Context.Interfaces.PlayerContextInterface;
import Models.Player;
import com.sun.media.jfxmedia.logging.Logger;
import jdk.nashorn.internal.runtime.options.LoggingOption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.io.*;
import java.util.Random;



/**
 * Created by Bryan on 27-3-2017.
 */
//test
public class PlayerMySQLContext implements PlayerContextInterface {

    private Connection con;

    private void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi356103", "dbi356103", "Kwibble");
        }
        catch (Exception exc){
            System.out.println(exc.getMessage());
            throw new RuntimeException("couldn't get the SQL connection");
        }
    }
    public PlayerMySQLContext() {

    }

    @Override
    public boolean Add(Player player) {
        try{
            initConnection();
            PreparedStatement myStatement = con.prepareStatement("INSERT INTO player (Email,Password,Salt,Score) VALUES ?, ?, ?,?");

            myStatement.setString(1,player.getEmail());

            //create random salt
            Random r = new Random();
            byte[] salt = new byte[32];
            r.nextBytes(salt);

            //encrypt the salt and password with md5
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            String completepass = player.getPassword() + salt.toString();
            m.update(completepass.getBytes());

            player.setPassword(m.toString());

            //set parameters
            myStatement.setString(2,player.getPassword());
            myStatement.setString(3,salt.toString());
            myStatement.setInt(4, player.getScore());
            myStatement.executeUpdate();

            con.close();
            myStatement.close();

        }
        catch(Exception exc){
            System.out.println(exc.getMessage());

            throw new RuntimeException("context");

        }
        return true;
    }
    public boolean Login(Player player){
        try{
            initConnection();
            PreparedStatement GetSalt = con.prepareStatement("SELECT Salt From Player WHERE Email = ?");
            GetSalt.setString(1,player.getEmail());

            ResultSet rs = GetSalt.executeQuery();
            if (rs.next()){
                String salt = rs.getString("Salt");
            }


            PreparedStatement myStatement = con.prepareStatement("SELECT Email, Password, Score FROM player WHERE Email = ? AND Password = ?");

            myStatement.setString(1,player.getEmail());
            myStatement.setString(2,player.getPassword());


            myStatement.executeUpdate();

            con.close();

        }
        catch(Exception exc){

            return false;
        }
        return true;
    }

    @Override
    public boolean Remove(Player player) {
        try{
            initConnection();

            PreparedStatement myStatement = con.prepareStatement("DELETE FROM player WHERE Email = ?");

            myStatement.setString(1,player.getEmail());

            myStatement.executeUpdate();

            con.close();

        }
        catch(Exception exc){

            return false;
        }
        return true;
    }


}
