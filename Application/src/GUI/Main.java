package GUI;

import Data.Context.QuestionMySQLContext;
import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import Data.Repos.QuestionRepository;
import Models.SimpleServer;
import com.sun.net.httpserver.HttpServer;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.Track;
import javafx.application.Application;
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
        QuestionRepository repo = new QuestionRepository(new QuestionMySQLContext());
        repo.getRandomQuestion();
    }
}
