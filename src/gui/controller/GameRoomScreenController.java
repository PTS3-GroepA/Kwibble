package gui.controller;

import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Difficulty;
import models.GameRoom;
import models.Player;
import models.Quiz;

import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;

/**
 * Created by Max Meijer on 22/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameRoomScreenController implements Initializable {

    @FXML
    ListView lvPlayers;
    @FXML
    Button btnHost;
    @FXML
    ComboBox cbDifficulty;
    @FXML
    Spinner spinNumberOfQuestions;
    @FXML
    TextField tfPlaylistURI;
    @FXML
    Label lblServerName;

    GameRoom room = null;

    void initData(String name, Player host) {
        room = new GameRoom(host , name);
        setServerName(name);

        lvPlayers.getItems().setAll(room.getPlayers().keySet());

        cbDifficulty.getItems().setAll(Difficulty.values());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void setServerName(String name) {
        lblServerName.setText("Server: " +  name);
    }

    private void addPlayer(Player player) {

    }

}
