package GUI.Controller;

import Data.Repos.MusicRepository;
import Models.MusicPlayer;
import Models.Questions.Question;
import Models.Quiz;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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

    void initData(Question question) {
        System.out.println("initialising game screen with question: " + question.toString());
        this.question = question;
        questionBox.setText(question.getQuestionString());
        placeAnswers();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        Platform.runLater(new MusicPlayer(question.getPreviewURL()));
    }
}
