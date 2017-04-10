package GUI.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Max Meijer on 10/04/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class MainMenuController implements Initializable {

    @FXML private Button btnJoinGame;
    @FXML private Button btnHostGame;
    @FXML private Button btnLocalGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnHostGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                System.out.println(e.toString());
            }
        });
    }
}
