package gui.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import models.Difficulty;
import models.GameRoom;
import models.Player;
import models.Quiz;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Max Meijer on 22/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameRoomScreenController implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(LocalGameController.class.getName());

    @FXML
    private ListView lvPlayers;
    @FXML
    private Button btnHost;
    @FXML
    private ComboBox cbDifficulty;
    @FXML
    private Spinner spinNumberOfQuestions;
    @FXML
    private TextField tfPlaylistURI;
    @FXML
    private Label lblServerName;

    GameRoom room = null;

    void initData(String name, Player host) {
        room = new GameRoom(host , name);
        setServerName(name);

        lvPlayers.getItems().setAll(room.getPlayers().keySet());

        cbDifficulty.getItems().setAll(Difficulty.values());

        final int initialValue = 5;

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20, initialValue);
        spinNumberOfQuestions.setValueFactory(valueFactory);

        spinNumberOfQuestions.setEditable(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void setServerName(String name) {
        lblServerName.setText("Server: " +  name);
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

    private void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setWidth(300);
        dialog.setHeight(100);
        dialog.showAndWait();
    }

    private void addPlayer(Player player) {

    }

}
