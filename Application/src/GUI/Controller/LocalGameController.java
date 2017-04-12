package GUI.Controller;

import Models.Difficulty;
import Models.Quiz;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * Created by Max Meijer on 10/04/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class LocalGameController implements Initializable {

    @FXML
    private TextField tfURI;
    @FXML
    private TextField tfNrQuestion;
    @FXML
    private Button btnPlay;
    @FXML
    private WebView webView;

    Quiz quiz;
    int questionPlayed;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        webView.setVisible(false);
        questionPlayed = 0;

        tfNrQuestion.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Make the textField numerical
                if (!newValue.matches("\\d*")) {
                    tfNrQuestion.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame(event);
            }
        });
    }

    private void startGame(ActionEvent event) {
        if (tfURI.getText().length() <= 0 || tfNrQuestion.getText().length() <= 0) {
            showDialog("Some field are blank");
            return;
        }

        String rawString = tfURI.getText();
        System.out.println(rawString);

        String trimUser = "";
        String uri = "";

        try {
            trimUser = rawString.substring(13, rawString.indexOf(':', 13));
            uri = rawString.substring(rawString.lastIndexOf(':') + 1);
        } catch (StringIndexOutOfBoundsException e) {
            showDialog("Invalid spotify playlist URI");
            e.printStackTrace();
            return;
        }

        System.out.println(trimUser);
        System.out.println(uri);

        quiz = new Quiz(Integer.parseInt(tfNrQuestion.getText()), Difficulty.EASY, trimUser, uri, this);

        if (!quiz.checkAuthorization()) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Spotify authorization");
            alert.setHeaderText("Spotify not authorised");
            alert.setContentText("It appears you are not logged in on spotify. Without authenticating you cannot play, do you want to getAuthenticationURL your account now?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                showBrowser(quiz.getAuthenticationURL());

            } else {
                showDialog("You will now return to main menu.");
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
                return;
            }
        }
    }

    public void confirmAuthorization() {
        System.out.println("Authorization confirmed");
        webView.setVisible(false);
        quiz.generateQuestions();
        playQuestion(questionPlayed);
    }

    public void showBrowser(String URL) {
        WebEngine webEngine = webView.getEngine();
        webEngine.load(URL);
        webView.setVisible(true);
    }

    public void showDialog(String message) {
        Dialog<String> dialog = new Dialog<>();
        dialog.getDialogPane().setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
        dialog.setWidth(300);
        dialog.setHeight(100);
        dialog.showAndWait();
    }

    private void playQuestion(int questionToPlay) {

        System.out.println("Opening play screen");
        Platform.runLater(() -> {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("GUI/Screens/GameScreen.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                GameScreenController controller = fxmlLoader.getController();
                controller.initData(quiz.getQuestion(questionToPlay));
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
                controller.playMusic();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void increasePlayedQuestion() {
        questionPlayed++;
    }
}
