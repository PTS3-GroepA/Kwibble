package models;

import gui.controller.GameScreenController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

/**
 * Created by Max on 03-Jun-17.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class SimpleTimer extends Task {

    double duration = 10;
    GameScreenController controller;
    ProgressBar bar;

    public SimpleTimer(double duration, GameScreenController controller, ProgressBar bar) {
        this.duration = duration;
        this.controller = controller;
        this.bar = bar;
    }

    @Override
    protected Object call() throws Exception {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(bar.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(duration), e-> {
                    controller.answerAndClose();
                    System.out.println("Timer finished");
                }, new KeyValue(bar.progressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        return null;
    }
}
