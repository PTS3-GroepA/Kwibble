package GUI;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.AlbumRequest;
import com.wrapper.spotify.methods.TrackRequest;
import com.wrapper.spotify.models.Album;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;
import com.wrapper.spotify.models.*;


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
        AudioFeatureRequest afr = api.

// Retrieve an album
        try {
            Track track = request.get();

            // Print the genres of the album
            List<SimpleArtist> artist = track.getArtists();
            String name=  track.getName();
            System.out.println(name);

            for (SimpleArtist a : artist) {
                System.out.println(a.getName());
            }

            track.

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get albums.");
        }
    }

}
