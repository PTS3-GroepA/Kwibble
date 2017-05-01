package GUI.Controller;

import Data.Repos.MusicRepository;
import Models.MusicPlayer;
import Models.Questions.Question;
import Models.Quiz;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

/**
 * Created by Max on 4/11/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameScreenController implements Initializable {

    @FXML private Button btnAnswer1;
    @FXML private Button btnAnswer2;
    @FXML private Button btnAnswer3;
    @FXML private Button btnAnswer4;
    @FXML private Label questionBox;

    private Question question;
    LocalGameController controllerReference;
    MusicPlayer mp;

    void initData(Question question ,LocalGameController controller) {
        System.out.println("initialising game screen with question: " + question.toString());
        this.question = question;
        this.controllerReference = controller;
        questionBox.setText(question.getQuestionString());
        placeAnswers();
        mp = new MusicPlayer(question.getPreviewURL());
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAnswer1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answerQuestion(btnAnswer1.getText())) {
                    btnAnswer1.setStyle("-fx-background-color: green;");
                    showResultDialog(true, event);
                }
                else {
                    btnAnswer1.setStyle("-fx-background-color: red;");
                    showResultDialog(false,event);
                }
            }
        });

        btnAnswer2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answerQuestion(btnAnswer2.getText())) {
                    btnAnswer2.setStyle("-fx-background-color: green;");
                    showResultDialog(true,event);
                }
                else {
                    btnAnswer2.setStyle("-fx-background-color: red;");
                    showResultDialog(false,event);
                }
            }
        });

        btnAnswer3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answerQuestion(btnAnswer3.getText())) {
                    btnAnswer3.setStyle("-fx-background-color: green;");
                    showResultDialog(true,event);
                }
                else {
                    btnAnswer3.setStyle("-fx-background-color: red;");
                    showResultDialog(false,event);
                }
            }
        });

        btnAnswer4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(answerQuestion(btnAnswer4.getText())) {
                    btnAnswer4.setStyle("-fx-background-color: green;");
                    showResultDialog(true,event);
                }
                else {
                    btnAnswer4.setStyle("-fx-background-color: red;");
                    showResultDialog(false,event);
                }
            }
        });

    }

    private void placeAnswers() {
        Platform.runLater(() -> {
        ArrayList<Integer> numbersHad = new ArrayList<Integer>();

        for(int i = 0; i < 4; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, 4);

            while(numbersHad.contains(randomNum)) {
                randomNum = ThreadLocalRandom.current().nextInt(0, 4);
            }

            numbersHad.add(randomNum);

            switch(i) {
                case 0:
                    btnAnswer1.setText(question.getAnswerString(randomNum));
                    break;
                case 1:
                    btnAnswer2.setText(question.getAnswerString(randomNum));
                    break;
                case 2:
                    btnAnswer3.setText(question.getAnswerString(randomNum));
                    break;
                case 3:
                    btnAnswer4.setText(question.getAnswerString(randomNum));
                    break;
            }
        }
        });

    }

    void playMusic() {
        System.out.println("Playing music");
        System.out.println(question.getPreviewURL());
        Platform.runLater(mp);
    }

    private boolean answerQuestion(String answer) {
        return question.answerQuestion(answer);
    }

    void showResultDialog(boolean correct, ActionEvent actionEvent) {
        mp.stop();
        if(correct) {
            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().setContentText("Well done!");
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
            dialog.setWidth(300);
            dialog.setHeight(100);
            dialog.showAndWait();
        } else {
            Dialog<String> dialog = new Dialog<>();
            dialog.getDialogPane().setContentText("Wrong, the correct answer was: " + question.getCorrectAnswer());
            dialog.getDialogPane().getButtonTypes().add(new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE));
            dialog.setWidth(300);
            dialog.setHeight(100);
            dialog.showAndWait();
        }

        controllerReference.increasePlayedQuestion();
        controllerReference.playQuestion();

        Node source = (Node)  actionEvent.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
