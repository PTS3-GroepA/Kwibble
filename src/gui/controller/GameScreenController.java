package gui.controller;

import fontyspublisher_kwibble.GameRoomCommunicator;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import models.MusicPlayer;
import models.SimpleTimer;
import models.questions.SerQuestion;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;


/**
 * Created by Max on 4/11/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameScreenController  implements Initializable  {

    @FXML
    private Button btnAnswer1;
    @FXML
    private Button btnAnswer2;
    @FXML
    private Button btnAnswer3;
    @FXML
    private Button btnAnswer4;
    @FXML
    private Label questionBox;
    @FXML
    public ProgressBar timerProgressBar;

    SimpleTimer timer;
    Thread timerThread;

    private GameRoomCommunicator communicator;
    private MusicPlayer mp;
    private SerQuestion question;

    void initData(SerQuestion question, GameRoomController controller, GameRoomCommunicator communicator) {
        this.question = question;
        this.communicator = communicator;
        questionBox.setText(question.getQuestionString());
        placeAnswers();
        mp = new MusicPlayer(question.getPreviewURL());
        timer = new SimpleTimer(question.getMaxAnswerTime(), this, timerProgressBar);

        timerThread = new Thread(timer);
        timerThread.start();
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnAnswer1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAnswer(event);
            }
        });

        btnAnswer1.setOnMouseEntered(mouseEvent -> btnAnswer1.setStyle("-fx-background-color: #954FA7"));


        btnAnswer2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAnswer(event);
            }
        });

        btnAnswer2.setOnMouseEntered(mouseEvent -> btnAnswer2.setStyle("-fx-background-color: #EFCA28"));

        btnAnswer3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAnswer(event);
            }
        });

        btnAnswer3.setOnMouseEntered(mouseEvent -> btnAnswer3.setStyle("-fx-background-color: #1FBB65"));

        btnAnswer4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                checkAnswer(event);
            }
        });

        btnAnswer4.setOnMouseEntered(mouseEvent -> btnAnswer4.setStyle("-fx-background-color:  #DF5242"));
    }

    private void placeAnswers() {
        Platform.runLater(() -> {
            ArrayList<Integer> numbersHad = new ArrayList<Integer>();

            for (int i = 0; i < 4; i++) {
                int randomNum = ThreadLocalRandom.current().nextInt(0, 4);

                while (numbersHad.contains(randomNum)) {
                    randomNum = ThreadLocalRandom.current().nextInt(0, 4);
                }

                numbersHad.add(randomNum);

                switch (i) {
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
        Platform.runLater(mp);
    }

    private boolean answerQuestion(String answer) {
        return question.answerQuestion(answer);
    }

    void showResultDialog(boolean correct, ActionEvent actionEvent) {
        timer.stopTimer();
        mp.stop();
        if (correct) {
            question.calculateScore(timerProgressBar.getProgress());
        } else {
            question.setScore(0);
        }

        Task<Void> sleeper = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                return null;
            }
        };
        sleeper.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                answerAndClose();
            }
        });
        new Thread(sleeper).start();


    }
    private void checkAnswer(ActionEvent event){
        Button b = (Button) event.getSource();
        if (answerQuestion(b.getText())) {
            b.setStyle("-fx-background-color: green;");
            showResultDialog(true, event);
        } else {
            b.setStyle("-fx-background-color: red;");

            showResultDialog(false, event);
        }
    }

    public void answerAndClose( ) {
        timer.stopTimer();
        communicator.broadcast("answer", question);
        Stage stage = (Stage) btnAnswer1.getScene().getWindow();
        stage.close();
    }
}
