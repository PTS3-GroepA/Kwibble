package gui.controller;

import fontyspublisher.RemotePublisher;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Max Meijer on 22/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class HostOptionsScreenController implements Initializable {

    @FXML
    public ComboBox cbDifficulty;
    @FXML
    public Spinner spinQuestionTime;
    @FXML
    private TextField tfHostName;
    @FXML
    private Button btnHost;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnHost.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startServer();
            }
        });
    }

    private void startServer() {
        int portNumber = 1099;

        if(Objects.equals(tfHostName.getText(), "")) {
            showDialog("Server name is empty.");
            return;
        }
        String name = tfHostName.getText();

        // Create an instance of RemotePublisher
        RemotePublisher remotePublisher = null;
        try {
            remotePublisher = new RemotePublisher();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.rebind(name, remotePublisher);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Remote publisher registered
        System.out.println("Server started");
        System.out.println("Port number  : " + portNumber);
        System.out.println("Binding name : " + name);
    }


    private void showDialog(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        Optional<ButtonType> result = alert.showAndWait();
    }
}
