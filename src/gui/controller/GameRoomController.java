package gui.controller;

import fontyspublisher_kwibble.GameRoomCommunicator;
import javafx.application.Platform;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Difficulty;
import models.GameRoom;
import models.Player;
import models.Quiz;
import org.omg.PortableInterceptor.ORBInitInfoPackage.DuplicateName;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;
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

    GameRoom room = null;
    GameRoomCommunicator communicator = null;
    private static String[] properties = {"room", "difficulty", "numberOfQuestions", "playlistUri", "join"};

    void initData(String name, Player host) {
        room = new GameRoom(host , name);

        cbDifficulty.getItems().setAll(Difficulty.values());
        setLvPlayers();

        spinNumberOfQuestions.setEditable(true);

        isConnected = true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // TODO make method to start playing
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
    @SuppressWarnings("Duplicates")
    private void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setWidth(300);
        dialog.setHeight(100);
        dialog.showAndWait();
    }

    public void addPlayer(Player player) {
        room.join(player);
    }

    private void cbDifficultyChangeEvent(Object newValue) {
        communicator.broadcast("difficulty", newValue);
    }

    private void spinChangeEvent(Object newValue) {
        communicator.broadcast("numberOfQuestions", newValue);
    }

    private void tfUriChangeEvent(Object newValue) {
        communicator.broadcast("playlistUri", newValue);
    }

    /**
     * Setup connection to the server and subscribe to all properties.
     *
     * @param serverName The name of the server to connect to.
     */
    public void connectAndSetup(String serverName) {
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
        spinNumberOfQuestions.setValueFactory(valueFactory);

        setServerName(serverName);

        try {
            communicator = new GameRoomCommunicator(this);
            communicator.connectToServer(serverName);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }

        for (String s : properties) {
            communicator.register(s);
            communicator.subscribe(s);
        }

        if(!isConnected) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            room = new GameRoom(serverName);
            TextInputDialog tid = new TextInputDialog("Player");
            tid.setHeaderText("Enter a player name: ");
            Optional<String> op = tid.showAndWait();
            String playerName = "";

            if (op.isPresent() && op.get().length() > 0) {
                playerName = op.get();
            } else {
                showDialog("Enter a valid player name.");
                return;
            }
            communicator.broadcast("join", null);
        }
    }

    public void setPlayers(Object value) {
        room.setPlayers((ObservableMap<Player, Boolean>) value);
        setLvPlayers();
    }

    public void setSpin(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                spinNumberOfQuestions.getValueFactory().setValue(value);
            }
        });
    }

    public void setUriText(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tfPlaylistURI.setText((String) value);
            }
        });
    }

    public void setCbDifficulty(Object value) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                cbDifficulty.setValue(value);
            }
        });
    }

    public void setLvPlayers() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                lvPlayers.getItems().setAll(room.getPlayers().keySet());
            }
        });
    }

    public void setRoom(Object value) {
        this.room = (GameRoom) value;
        setLvPlayers();
    }

    public void synchronise() {
        communicator.broadcast("room", room);
        communicator.broadcast("difficulty" , cbDifficulty.getValue());
        communicator.broadcast("playlistUri" , tfPlaylistURI.getText());
        communicator.broadcast("numberOfQuestions" , spinNumberOfQuestions.getValue());
    }
}
