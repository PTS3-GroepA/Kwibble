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
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import models.questions.SerQuestion;

import java.io.IOException;
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
    @FXML
    public CheckBox cbDisableMusic;
    @FXML
    private ProgressIndicator bar;
    @FXML
    private Pane blurPane;
    @FXML
    private Label labelLoadingQuestion;

    GameRoom room = null;
    GameRoomCommunicator communicator = null;
    // TODO change string array to enum.
    private static String[] properties = {"room", "difficulty", "numberOfQuestions", "playlistUri", "join", "leave", "playQuestion", "answer", "finished"};
    int answeredQuestions = 0;

    void initData(String name, Player host) {
        room = new GameRoom(host, name);
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
        bar.setVisible(false);
        blurPane.setVisible(false);
        labelLoadingQuestion.setVisible(false);

        tfPlaylistURI.setText("spotify:user:siemke1994:playlist:6R5Z9DppfB0WLjboQzl5cg");

        // Button start click event.
        btnStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                disableControls();
                addQuiz();
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
        cbDifficulty.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            cbDifficultyChangeEvent(newValue);
        });

        try {
            // Event when number of questions changes.
            spinNumberOfQuestions.valueProperty().addListener((obs, oldValue, newValue) -> {
                spinChangeEvent(newValue);
            });
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

        // Event when the uri changes.
        tfPlaylistURI.textProperty().addListener((observable, oldValue, newValue) -> {
            tfUriChangeEvent(newValue);
        });
    }

    private void authorize() {
        String url = room.quiz.getAuthenticationURL();
        WebEngine engine = webView.getEngine();
        engine.load(url);
        webView.setVisible(true);
    }

    public ProgressIndicator getProgressBar() {
        webView.setVisible(false);
        labelLoadingQuestion.setVisible(true);
        blurPane.setVisible(true);
        bar.setVisible(true);
        return bar;
    }

    public void confirmAuth() {
        bar.setVisible(false);
        blurPane.setVisible(false);
        webView.setVisible(false);
        labelLoadingQuestion.setVisible(false);
        playquestion();
    }

    public void playquestion() {
        answeredQuestions = 0;
        communicator.broadcast("playQuestion", room.quiz.getQuestionToPlay().getSerQuestion());
    }

    /**
     * Set the server name label at the top of the screen.
     *
     * @param name The name of the server.
     */
    private void setServerName(String name) {
        lblServerName.setText(name);
    }

    private void addQuiz() {
        int amountOfQuestions = (int) spinNumberOfQuestions.getValue();
        Difficulty dif = (Difficulty) cbDifficulty.getValue();
        ArrayList<String> result = (ArrayList<String>) trimUri();

        if (result == null) {
            return;
        }
        Quiz quiz = new Quiz(amountOfQuestions, dif, result.get(0), result.get(1));
        room.addQuiz(quiz);
        room.quiz.setGameBrowserController(this);

        authorize();
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
     *
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
     *
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
        communicator.broadcast("leave", localPlayer);
        backToMainMenu();
    }

    public void removePlayer(Object player) {
        room.leave((Player) player);
        synchronise();
    }

    /**
     * Broadcast the difficulty setting when it changes.
     *
     * @param newValue The new value of difficulty.
     */
    private void cbDifficultyChangeEvent(Object newValue) {
        communicator.broadcast("difficulty", newValue);
    }

    /**
     * Broadcast the number of questions when it changes.
     *
     * @param newValue The new number of questions.
     */
    private void spinChangeEvent(Object newValue) {
        try {
            communicator.broadcast("numberOfQuestions", newValue);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Broadcast the new uri value when it changes.
     *
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
    public void connectAndSetup(String ip, String serverName) {

        try {
            // Setup the spinner with a valueFactory otherwise we get a null point exception.
            SpinnerValueFactory<Integer> valueFactory =
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, 5);
            spinNumberOfQuestions.setValueFactory(valueFactory);
        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }

        // Set label on top of the screen.
        setServerName(serverName);

        // Setup the communicator and connect to the server.
        try {
            communicator = new GameRoomCommunicator(this);
            communicator.connectToServer(ip, serverName);
        } catch (RemoteException e) {
            LOGGER.severe(e.getMessage());
        }

        // Register and subscribe to all the properties except join.
        // Only the host will subscribe to join and will synchronise the other listeners.


        for (String s : properties) {
            communicator.register(s);
            if (!s.equals("join")) {
                communicator.subscribe(s);
            }
        }

        if (isHost) {
            communicator.subscribe("join");
        }

        // This statement is a bit fuzzy.
        // It will basically check if the connector is new to the server.
        // If this is the case it will prompt the user for a name and push the join property.
        if (!isConnected) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
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
     *
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
     *
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
     *
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
                for (Map.Entry<Player, Boolean> entry : room.getPlayers().entrySet()) {
                    Player key = entry.getKey();
                    Boolean value = entry.getValue();

                    String itemToAdd = "";

                    itemToAdd = key.getName();
                    if (value) {
                        itemToAdd = itemToAdd + " (host)";
                    }

                    if (key.getName().equals(localPlayer.getName())) {
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
     *
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
            LOGGER.log(Level.SEVERE, e.toString(), e);
        }
    }

    /**
     * Disable all the setting controls.
     */
    private void disableControls() {
        cbDifficulty.setDisable(true);
        cbDifficulty.setStyle("-fx-opacity: 1;");
        spinNumberOfQuestions.setDisable(true);
        spinNumberOfQuestions.setStyle("-fx-opacity: 1;");
        tfPlaylistURI.setDisable(true);
        tfPlaylistURI.setStyle("-fx-opacity: 1;");
        btnStart.setDisable(true);
    }


    /**
     * Add the new localPlayer to the room and send over all current data.
     */
    private void synchronise() {
        System.out.println("Synchronising");
        communicator.broadcast("room", room);
        communicator.broadcast("difficulty", cbDifficulty.getValue());
        communicator.broadcast("playlistUri", tfPlaylistURI.getText());
        communicator.broadcast("numberOfQuestions", spinNumberOfQuestions.getValue());
    }

    @SuppressWarnings("Duplicates")
    public void playQuestion(SerQuestion question) {

        question.setPlayer(localPlayer);

        System.out.println("Opening play screen");
        Platform.runLater(() -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/GameScreen.fxml"));
                Parent root1 = fxmlLoader.load();
                GameScreenController controller = fxmlLoader.getController();
                controller.initData(question, this, communicator);
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
                if (!cbDisableMusic.isSelected()) {
                    controller.playMusic();
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });
    }

    private void printScores() {
        for (Map.Entry<Player, Boolean> entry : room.getPlayers().entrySet()) {
            System.out.println("Player: " + entry.getKey().getName() + " has :" + entry.getKey().getScore() + " points!");
        }
    }


    public void showScoreScreen(ArrayList<Player> playersToShow) {
        Platform.runLater(() -> {
            try {
                Stage stageToHide = (Stage) btnStart.getScene().getWindow();
                stageToHide.close();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("gui/screens/GameScore.fxml"));
                Parent root1 = fxmlLoader.load();
                GameScoreController controller = fxmlLoader.getController();
                controller.initData(playersToShow);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setTitle("Score");
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.toString(), e);
            }
        });
    }

    public void answerQuestion(SerQuestion question) {
        try {
            System.out.println("Answering for player: " + question.getPlayer().getName());
            room.increaseScoreForPlayer(question.getPlayer(), question.getScore());
            answeredQuestions++;
            System.out.println(room.quiz.getQuestionsPlayed());

            if (answeredQuestions == room.getPlayers().size()) {

                if ((room.quiz.getQuestionsPlayed() + 1) == room.quiz.getQuestions().size()) {
                    System.out.println("game finished");
                    ArrayList<Player> list = new ArrayList<Player>(room.getPlayers().keySet());
                    Collections.sort(list, new ScoreComparer());
                    Collections.reverse(list);
                    communicator.broadcast("finished", list);
                    printScores();
                }
                printScores();
                room.quiz.increaseQuestionToPlay();
                playquestion();
            }

        } catch (Exception e) {
            LOGGER.severe(e.getMessage());
        }
    }
}
