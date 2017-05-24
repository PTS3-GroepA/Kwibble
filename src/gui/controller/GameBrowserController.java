package gui.controller;

import fontyspublisher_kwibble.GameRoomCommunicator;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max on 23-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameBrowserController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger(GameBrowserController.class.getName());


    @FXML
    public ListView lvServer;
    @FXML
    public Button btnJoin;
    @FXML
    public Button btnRefresh;

    private GameRoomCommunicator communicator = null;
    private ObservableList<String> servers = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            communicator = new GameRoomCommunicator(new GameRoomController());
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnRefreshEventHandler();
            }
        });

        btnJoin.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnHostEvenHandler();
            }
        });
    }

    private void btnRefreshEventHandler() {
        servers.clear();
        String[] foundServers = communicator.findServers();
        servers.addAll(foundServers);
        lvServer.setItems(servers);
    }

    private void btnHostEvenHandler() {
        openGameBrowserScreen();
    }

    private void openGameBrowserScreen() {
        Platform.runLater(() -> {
            try {
                Stage stageToHide = (Stage) btnJoin.getScene().getWindow();
                stageToHide.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/GameRoom.fxml"));
                Parent root1 = fxmlLoader.load();
                GameRoomController controller = fxmlLoader.getController();
                controller.connectAndSetup((String) lvServer.getSelectionModel().getSelectedItem());
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Game Room");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (IOException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });
    }
}
