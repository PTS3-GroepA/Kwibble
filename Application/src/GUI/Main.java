package GUI;

import Player.decoder.JavaLayerException;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.TrackRequest;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;

import javax.print.DocFlavor;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class Main {

    public static void main(String[] args) {
// Create an API instance. The default instance connects to https://api.spotify.com/.
        final String clientId = "037636c06b1c4348b69fa5646304de02";
        final String clientSecret = "2b9d9f8c3a8248c19d8064e3e1ee7bed";

        final Api api = Api.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();

// Create a request object for the type of request you want to make
        TrackRequest request = api.getTrack("73sWs9sacZj2TeZK3p5RxZ").build();


// Retrieve an album
        try {
            Track track = request.get();

            // Print the genres of the album
            List<SimpleArtist> artist = track.getArtists();
            String name=  track.getName();
            System.out.println(name);
            System.out.println(track.getPreviewUrl());

            for (SimpleArtist a : artist) {
                System.out.println(a.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get albums.");
        }
    }

    public void play() {
        String song = "http://www.ntonyx.com/mp3files/Morning_Flower.mp3";
        Player.player.Player mp3player = null;
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new URL(song));
            mp3player = new Player.player.Player(in);
            mp3player.play();
        } catch (MalformedURLException ex) {
        } catch (IOException e) {
        } catch (JavaLayerException e) {
        } catch (NullPointerException ex) {
        }

    }

}
