package gui.controller;

import fontyspublisher.RemotePublisher;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Player;

import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 22/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class HostOptionsScreenController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(HostOptionsScreenController.class.getName());

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

        String pcName = "";
        try {
            java.net.InetAddress localMachine = java.net.InetAddress.getLocalHost();
            pcName = localMachine.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        tfHostName.setText("Server on: " + pcName);
    }

    private void startServer() {
        int portNumber = 1099;

        if (Objects.equals(tfHostName.getText(), "")) {
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

        showGameRoomScreen();
    }


    private void showDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Warning");
        alert.setHeaderText("Warning");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showGameRoomScreen() {
        TextInputDialog tid = new TextInputDialog();
        tid.setHeaderText("Enter a player name: ");
        tid.setContentText("Player");
        Optional<String> op = tid.showAndWait();
        String name = "";

        if (op.isPresent() && op.get().length() > 0) {
            name = op.get();
        } else {
            showDialog("Enter a valid player name.");
            return;
        }
        Player player = new Player(name, 0);

        Platform.runLater(() -> {
            try {
                Stage stageToHide = (Stage) btnHost.getScene().getWindow();
                stageToHide.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/GameRoomScreen.fxml"));
                Parent root1 = fxmlLoader.load();
                GameRoomScreenController controller = fxmlLoader.getController();
                controller.initData(tfHostName.getText(), player);
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
