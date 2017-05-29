package gui.controller;

import fontyspublisher_kwibble.GameRoomCommunicator;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Difficulty;
import models.GameRoom;
import models.Player;
import models.Quiz;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Thread.sleep;

/**
 * Created by Max Meijer on 22/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameRoomController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(LocalGameController.class.getName());

    private boolean isConnected = false;
    private boolean isHost = false;
    private Player localPlayer = null;

    private Thread thread;

    @FXML
    public Button btnLeave;
    @FXML
    private ListView lvPlayers;
    @FXML
    private Button btnStart;
    @FXML
    private ComboBox cbDifficulty;
    @FXML
    private Spinner spinNumberOfQuestions;
    @FXML
    private TextField tfPlaylistURI;
    @FXML
    private Label lblServerName;
    @FXML
    private WebView webView;

    GameRoom room = null;
    GameRoomCommunicator communicator = null;
    // TODO change string array to enum.
    private static String[] properties = {"room", "difficulty", "numberOfQuestions", "playlistUri", "join", "leave"};

    void initData(String name, Player host) {
        room = new GameRoom(host , name);
        localPlayer = host;

        cbDifficulty.getItems().setAll(Difficulty.values());
        setLvPlayers();

        spinNumberOfQuestions.setEditable(true);

        isConnected = true;
        isHost = true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView.setVisible(false);

        // Button start click event.
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addQuiz();
                authorize();
            }
        });

        // Button leave click event.
        btnLeave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                leave();
            }
        });

        // Event when difficulty changes.
        cbDifficulty.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
            cbDifficultyChangeEvent(newValue);
        });

        // Event when number of questions changes.
        spinNumberOfQuestions.valueProperty().addListener((obs, oldValue, newValue) -> {
                spinChangeEvent(newValue);
        });

        // Event when the uri changes.
        tfPlaylistURI.textProperty().addListener((observable, oldValue, newValue) -> {
            tfUriChangeEvent(newValue);
        });
    }



    private void authorize(){

        String url = room.quiz.getAuthenticationURL();
        WebEngine engine = webView.getEngine();
        engine.load(url);
        webView.setVisible(true);

        thread = new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });

    }

    /**
     * Set the server name label at the top of the screen.
     * @param name The name of the server.
     */
    private void setServerName(String name) {
        lblServerName.setText(name);
    }

    private void addQuiz() {
        int amountOfQuestions = (int) spinNumberOfQuestions.getValue();
        Difficulty dif = (Difficulty) cbDifficulty.getValue();
        ArrayList<String> result = (ArrayList<String>) trimUri();

        Quiz quiz = new Quiz(amountOfQuestions, dif, result.get(0), result.get(1));
        room.addQuiz(quiz);

    }

    /**
     * Get the userID and the playlist URI from the spotify string.
     *
     * @return A list where the first item is the userID and the second the playlist URI
     */
    private List<String> trimUri() {
        String rawString = tfPlaylistURI.getText();
        System.out.println(rawString);

        ArrayList<String> result = new ArrayList<>();

        String trimUser = "";
        String uri = "";

        try {
            trimUser = rawString.substring(13, rawString.indexOf(':', 13));
            uri = rawString.substring(rawString.lastIndexOf(':') + 1);

            result.add(trimUser);
            result.add(uri);
        } catch (StringIndexOutOfBoundsException e) {
            showDialog("Invalid spotify playlist URI");
            LOGGER.log(Level.SEVERE, e.toString(), e);
            return null;
        }

        return result;
    }

    /**
     * Prompt a warning to the user.
     * @param message The warning message to display.
     */
    @SuppressWarnings("Duplicates")
    private void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setWidth(300);
        dialog.setHeight(100);
        dialog.showAndWait();
    }

    /**
     * Add localPlayer to the game room.
     * @param player The localPlayer to add
     */
    public void addPlayer(Object player) {
        room.join((Player) player);
        synchronise();
    }

    /**
     * Leave the current server
     */
    private void leave() {
        communicator.broadcast("leave" , localPlayer);
        backToMainMenu();
    }

    public void removePlayer(Object player) {
        room.leave((Player) player);
        synchronise();
    }

    /**
     * Broadcast the difficulty setting when it changes.
     * @param newValue The new value of difficulty.
     */
    private void cbDifficultyChangeEvent(Object newValue) {
        communicator.broadcast("difficulty", newValue);
    }

    /**
     * Broadcast the number of questions when it changes.
     * @param newValue The new number of questions.
     */
    private void spinChangeEvent(Object newValue) {
        communicator.broadcast("numberOfQuestions", newValue);
    }

    /**
     * Broadcast the new uri value when it changes.
     * @param newValue The new value of uri.
     */
    private void tfUriChangeEvent(Object newValue) {
        communicator.broadcast("playlistUri", newValue);
    }

    /**
     * Setup connection to the server and subscribe to all properties.
     *
     * @param serverName The name of the server to connect to.
     */
    public void connectAndSetup(String serverName) {

        // Setup the spinner with a valueFactory otherwise we get a null point exception.
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        spinNumberOfQuestions.setValueFactory(valueFactory);

        // Set label on top of the screen.
        setServerName(serverName);

        // Setup the communicator and connect to the server.
        try {
            communicator = new GameRoomCommunicator(this);
            communicator.connectToServer(serverName);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }

        // Register and subscribe to all the properties except join.
        // Only the host will subscribe to join and will synchronise the other listeners.


        for (String s : properties) {
            communicator.register(s);
            if(!s.equals("join")) {
                communicator.subscribe(s);
            }
        }

        if(isHost) {
            communicator.subscribe("join");
        }

        // This statement is a bit fuzzy.
        // It will basically check if the connector is new to the server.
        // If this is the case it will prompt the user for a name and push the join property.
        if(!isConnected) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            room = new GameRoom(serverName);
            TextInputDialog tid = new TextInputDialog("Player");
            tid.setHeaderText("Enter a localPlayer name: ");
            Optional<String> op = tid.showAndWait();
            String playerName = "";

            if (op.isPresent() && op.get().length() > 0) {
                playerName = op.get();
            } else {
                showDialog("Enter a valid localPlayer name.");
                return;
            }

            // Create the new localPlayer and push it on join.
            // The server will handle the join event.
            Player newPlayer = new Player(playerName, 0);
            localPlayer = newPlayer;
            communicator.broadcast("join", newPlayer);

            // Disable the controls so only the host can change them.
            disableControls();
        }
    }

    /**
     * Set the spinner value.
     * @param value The value to set the spinner to.
     */
    public void setSpin(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                spinNumberOfQuestions.getValueFactory().setValue(value);
            }
        });
    }

    /**
     * Set the tfUri value.
     * @param value The value to set the textfield to.
     */
    public void setUriText(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfPlaylistURI.setText((String) value);
            }
        });
    }

    /**
     * Set the combo box value.
     * @param value The value to set the cb to.
     */
    public void setCbDifficulty(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cbDifficulty.setValue(value);
            }
        });
    }

    /**
     * Set the list view items based on the localPlayer map in room.
     */
    public void setLvPlayers() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                ArrayList<String> items = new ArrayList<>();
                for(Map.Entry<Player, Boolean> entry : room.getPlayers().entrySet()) {
                    Player key = entry.getKey();
                    Boolean value = entry.getValue();

                    String itemToAdd = "";

                    itemToAdd = key.getName();
                    if(value) {
                        itemToAdd = itemToAdd + " (host)";
                    }

                    if(key.getName().equals(localPlayer.getName())) {
                        itemToAdd = itemToAdd + " (you)";
                    }

                    items.add(itemToAdd);
                }
                lvPlayers.getItems().setAll(items);
            }
        });
    }

    /**
     * Set the room to the new value.
     * @param value The new GameRoom value.
     */
    public void setRoom(Object value) {
        this.room = (GameRoom) value;
        setLvPlayers();
    }

    private void backToMainMenu() {
        try {
            Stage stageToHide = (Stage) btnStart.getScene().getWindow();
            stageToHide.close();

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/MainMenu.fxml"));
            Parent root1 = null;
            root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Kwibble");
            stage.setScene(new Scene(root1));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Disable all the setting controls.
     */
    private void disableControls() {
        cbDifficulty.setDisable(true);
        spinNumberOfQuestions.setDisable(true);
        tfPlaylistURI.setDisable(true);
    }


    /**
     * Add the new localPlayer to the room and send over all current data.
     */
    private void synchronise() {
        System.out.println("Synchronising");
        communicator.broadcast("room", room);
        communicator.broadcast("difficulty" , cbDifficulty.getValue());
        communicator.broadcast("playlistUri" , tfPlaylistURI.getText());
        communicator.broadcast("numberOfQuestions" , spinNumberOfQuestions.getValue());
    }
}
