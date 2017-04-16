package Models;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Map;

/**
 * Created by Max on 4/7/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 *
 * A simple music player that can play a media from a webURL and a file.
 */
public class MusicPlayer implements Runnable {

    private MediaPlayer player;
    private String source;

    public MusicPlayer(String source) {
        this.source = source;
        System.out.println("Music player running");
        Media m = new Media(source);
        player = new MediaPlayer(m);
    }

    /**
     * The source file or URL to play.
     *
     * @param source The location of the source.
     */
    public void setSource(String source) {
        this.source = source;
    }


    /**
     * Stop the music player.
     */
    public void stop() {
        player.stop();
    }

    @Override
    public void run() {
        Platform.setImplicitExit(false);
        if (player.getError() == null)
        {
            player.setOnError(() -> player.getError().printStackTrace());
            player.setAutoPlay(true);
        }
    }
}
