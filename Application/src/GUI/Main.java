package GUI;

import Data.Context.SpotifyContext;
import Data.Repos.MusicRepository;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.PlaylistTrack;
import com.wrapper.spotify.models.Track;
import javafx.application.Application;
import javafx.stage.Stage;



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
        try {
           musicRepo.clientAuthorise();
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}
