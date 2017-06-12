package gui.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 10/04/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class MainMenuController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(MainMenuController.class.getName());
    @FXML
    private Button btnJoinGame;
    @FXML
    private Button btnHostGame;
    @FXML
    private Button btnLocalGame;
    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnHostGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                openHostOptions();
            }
        });

        btnJoinGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openGameBrowser();
            }
        });
        btnLocalGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                openLocalGame();
            }
        });

    }

    private void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("Ok", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.show();
    }

    private void openLocalGame() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/LocalGame.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Local game settings");
            stage.setScene(new Scene(root1));
            stage.show();


        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void openHostOptions() {
        try {
            Stage stageToHide = (Stage) btnHostGame.getScene().getWindow();
            stageToHide.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/HostOptions.fxml"));
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Host options");
            stage.setScene(new Scene(root1 ));
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    private void openGameBrowser() {
        try {
            Stage stageToHide = (Stage) btnHostGame.getScene().getWindow();
            stageToHide.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/GameBrowser.fxml"));
            Parent root1 = null;
            root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Game browser");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }

    }
}
