package gui.controller;

import fontyspublisher_kwibble.GameBrowserCommunicator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

/**
 * Created by Max on 23-May-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameBrowserController implements Initializable {

    @FXML
    public ListView lvServer;
    @FXML
    public Button btnJoin;
    @FXML
    public Button btnRefresh;

    GameBrowserCommunicator communicator = null;
    ObservableList<String> servers = FXCollections.observableArrayList ();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            communicator = new GameBrowserCommunicator();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnRefreshEventHandler();
            }
        });
    }

    private void btnRefreshEventHandler() {
       String server = communicator.findServers();
       servers.add(server);
       lvServer.setItems(servers);
    }
}
