package GUI;

import GUI.Controller.MainMenuController;
import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.util.Duration;


/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    String style;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/Screens/MainMenu.fxml"));
        stage.setTitle("Kwibble");
        Scene scene =new Scene(root, 1200, 800);
        scene.getStylesheets().add(getClass().getResource("Screens/Main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
// geprobeerd om iets met die achtergrondklleur te doen maar werkt niet.
       // http://stackoverflow.com/questions/24587342/how-to-animate-lineargradient-on-javafx
//        ObjectProperty<Color> baseColor = new SimpleObjectProperty<>();
//
//        KeyValue keyValue1 = new KeyValue(baseColor, Color.RED);
//        KeyValue keyValue2 = new KeyValue(baseColor, Color.YELLOW);
//        KeyFrame keyFrame1 = new KeyFrame(Duration.ZERO, keyValue1);
//        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(500), keyValue2);
//        Timeline timeline = new Timeline(keyFrame1,keyFrame2);
//
//        baseColor.addListener((obs, oldColor, newColor) -> {
//            root.lookup("AnchorPane").getStyleClass().add(String.format("-gradient-base: #%02x%02x%02x; ",
//                    (int)(newColor.getRed()*255),
//                    (int)(newColor.getGreen()*255),
//                    (int)(newColor.getBlue()*255)));
//        });
//        timeline.setAutoReverse(true);
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();

    }
}
