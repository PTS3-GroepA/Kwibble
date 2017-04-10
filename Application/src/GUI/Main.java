package GUI;

import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Models.*;
import com.wrapper.spotify.models.Track;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;


/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 */
public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("GUI/Screens/MainMenu.fxml"));

        stage.setTitle("FXML Welcome");
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();


        MusicRepository musicRepo = new MusicRepository(new SpotifyContext());
        Quiz quiz = new Quiz(10, Difficulty.EASY, "ursulaboy", "4Y9FDNadgIa7n3ktJejcsW");
        quiz.setAmountOfQuestions(5);
        quiz.authenticate();

        quiz.generateQuestions();
        Track track =  musicRepo.getTrack(quiz.getQuestions().get(0).getSource());

        Media m = new Media(track.getPreviewUrl());
        MediaPlayer player = new MediaPlayer(m);
        player.setVolume(5);
        player.play();
    }
}
