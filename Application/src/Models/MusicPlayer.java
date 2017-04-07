package Models;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Created by Max on 4/7/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 *
 * A simple music player that can play a media from a webURL and a file.
 */
public class MusicPlayer {

    private MediaPlayer player;

    public MusicPlayer() {

    }

    /**
     * Play a media file from an url
     *
     * @param URI The spotify 30 second preview URI
     */
    public void play(String URI) {
        System.out.println("Playing song: " + URI);
        Media m = new Media(URI);
        player = new MediaPlayer(m);
        player.play();
    }

    /**
     * Stop the music player.
     */
    public void stop() {
        player.stop();
    }
}
