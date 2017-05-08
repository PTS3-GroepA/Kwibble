package Data.Context;

import Data.Context.Interfaces.PlayerContextInterface;
import Models.Player;

import com.mysql.jdbc.*;
import jdk.nashorn.internal.runtime.options.LoggingOption;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.*;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Random;


import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Bryan on 27-3-2017.
 */

public class PlayerMySQLContext implements PlayerContextInterface {

    private Connection con;
    private static final Logger LOGGER = Logger.getLogger( PlayerMySQLContext.class.getName());

    private void initConnection(){
        try{
            con = DriverManager.getConnection("jdbc:mysql://studmysql01.fhict.local/dbi356103", "dbi356103", "Kwibble");
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    @Override
    public boolean Add(Player player) {
        PreparedStatement AddPlayer = null;
        try{
            initConnection();

            AddPlayer = con.prepareStatement("INSERT INTO player (Email,Password,Salt,Score) VALUES ?, ?, ?,?");
            AddPlayer.setString(1,player.getEmail());

            //create random salt
            Random r = new Random();
            byte[] salt = new byte[32];
            r.nextBytes(salt);

            //encrypt the salt and password with md5
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            
            String completepass = player.getPassword() + Arrays.toString(salt);
            m.update(completepass.getBytes());

            player.setPassword(m.toString());

            //set parameters
            AddPlayer.setString(2,player.getPassword());
            AddPlayer.setString(3,Arrays.toString(salt));
            AddPlayer.setInt(4, player.getScore());
            AddPlayer.executeUpdate();



        }
        catch(Exception e){
            System.out.println(e.getMessage());
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
        finally {
            try {
                if (AddPlayer != null ){
                    AddPlayer.close();
                }

                con.close();
            } catch (SQLException e) {

                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        }
        return true;
    }
    public boolean Login(Player player) {

        PreparedStatement GetSalt = null;
        PreparedStatement myStatement = null;
        try {
            initConnection();
            GetSalt = con.prepareStatement("SELECT Salt From Player WHERE Email = ?");
            GetSalt.setString(1, player.getEmail());

            ResultSet rs = GetSalt.executeQuery();
            if (rs.next()) {
                String salt = rs.getString("Salt");
            }
            myStatement = con.prepareStatement("SELECT Email, Password, Score FROM player WHERE Email = ? AND Password = ?");

            myStatement.setString(1, player.getEmail());
            myStatement.setString(2, player.getPassword());

            myStatement.executeUpdate();

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return false;
        } finally {

            try {
                con.close();
                    if (GetSalt != null){
                        GetSalt.close();
                    }
                    if (myStatement != null){
                        myStatement.close();
                    }

            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);

            }

        }
        return true;
    }
    public boolean Remove (Player player) {
        PreparedStatement RemovePlayer = null;
        try{
            initConnection();

            RemovePlayer = con.prepareStatement("DELETE FROM player WHERE Email = ?");

            RemovePlayer.setString(1,player.getEmail());

            RemovePlayer.executeUpdate();

            con.close();

        }
        catch(Exception e){
            if (RemovePlayer != null ){
                try {
                    RemovePlayer.close();
                } catch (SQLException e1) {
                    LOGGER.log(Level.SEVERE, e.toString(), e);
                }
            }
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return false;
        }
        return true;
    }


}
