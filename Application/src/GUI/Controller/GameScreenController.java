package GUI.Controller;

import Models.Questions.Question;
import Models.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Max on 4/11/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class GameScreenController implements Initializable {

    @FXML private Button btnAnswer1;
    @FXML private Button btnAnswer2;
    @FXML private Button btnAnswer3;
    @FXML private Button btnAnswer4;
    @FXML private TextField questionBox;

    private Question question;

    public void initData (Question question) {
        System.out.println("initialising game screen with question: " + question.toString());
        this.question = question;
        questionBox.setText(question.getQuestionString());
        placeAnswers();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    private void placeAnswers() {
        while(btnAnswer1.getText() == "Answer1" || btnAnswer2.getText() == "Answer2" || btnAnswer3.getText() == "Answer3" || btnAnswer4.getText() == "Answer4") {
            int randomNum = ThreadLocalRandom.current().nextInt(1, 4);
            System.out.println(randomNum);
            switch (randomNum) {
                case 1:
                    if(btnAnswer1.getText() != "Answer1")
                        btnAnswer1.setText(question.getAnswerString(0));
                    break;
                case 2:
                    if(btnAnswer2.getText() != "Answer2")
                        btnAnswer2.setText(question.getAnswerString(1));
                    break;
                case 3:
                    if(btnAnswer3.getText() != "Answer3")
                        btnAnswer3.setText(question.getAnswerString(2));
                    break;
                case 4:
                    if(btnAnswer4.getText() != "Answer4")
                        btnAnswer4.setText(question.getAnswerString(3));
                    break;
            }
        }
    }
}
