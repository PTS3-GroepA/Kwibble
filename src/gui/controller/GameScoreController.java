package gui.controller;

import fontyspublisher_kwibble.GameRoomCommunicator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import models.MusicPlayer;
import models.Player;
import models.questions.SerQuestion;

import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Max Meijer on 31/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameScoreController implements Initializable {

    private ArrayList<Player> players;
    private GridPane grid;
    @FXML
    private AnchorPane pane;

    void initData(ArrayList<Player> players) {
        this.players = players;

        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        for (int i = 0; i < players.size(); i ++)
        {;
            Label label = new Label(players.get(i).getName() + ": " + players.get(i).getScore());
            label.setFont(new Font("Arial", 20));
            grid.add(label, 22 , 10 + (i * 2));
        }

        pane.getChildren().add(grid);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


}
