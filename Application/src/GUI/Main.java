package GUI;

import Data.Context.QuestionMySQLContext;
import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Data.Repos.QuestionRepository;
import Models.*;
import com.sun.net.httpserver.HttpServer;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.Track;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import static java.lang.Thread.sleep;


/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 * Edit made on branch Max 
 */
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
