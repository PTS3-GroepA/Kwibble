package models;

import javafx.application.Platform;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.logging.Logger;

/**
 * Created by Max on 4/7/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 * <p>
 * A simple music player that can play a media from a webURL and a file.
 */
public class MusicPlayer implements Runnable {

    private MediaPlayer player;
    private String source;

    public MusicPlayer(String source) {
        this.source = source;
        System.out.println("Music player running");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Media m = null;
        try {
            m = new Media(source);
        } catch(Exception e) {
            Logger.getAnonymousLogger().severe(e.getMessage());
            m = new Media("https://p.scdn.co/mp3-preview/b51d0ed637d2e5cb3eb27565cce9a06f95599077");
        }
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
        if (player.getError() == null) {
            player.setOnError(() -> player.getError().printStackTrace());
            player.setAutoPlay(true);
        }
    }
}
