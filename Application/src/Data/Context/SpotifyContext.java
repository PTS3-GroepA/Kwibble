package Data.Context;

import Data.Context.Interfaces.MusicContext;
import Models.SimpleServer;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.ArtistRequest;
import com.wrapper.spotify.methods.PlaylistRequest;
import com.wrapper.spotify.methods.TrackRequest;
import com.wrapper.spotify.models.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Max Meijer on 03/04/2017.
 * Fontys University of Applied Sciences, Eindhoven
 *
 * Getting all spotify related data is done by using thelinmichael spotify-web-api-java wrapper.
 * Source: https://github.com/thelinmichael/spotify-web-api-java.
 * All license information can be found by visiting the link above.
 */
public class SpotifyContext implements MusicContext {

    private Api api;

    public SpotifyContext() {

        String clientId = "037636c06b1c4348b69fa5646304de02";
        // Super Top Secret
        String clientSecret = "2b9d9f8c3a8248c19d8064e3e1ee7bed";

        // Create an API instance. The default instance connects to https://api.spotify.com/.
        api = Api.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    @Override
    public Track getTrack(String URI) {
        // Create a request object for the type of request you want to make
        TrackRequest request = api.getTrack(URI).build();

        try {
            return request.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Artist getArtist(String URI) {
        // Create a request object for the type of request you want to make
        ArtistRequest request = api.getArtist(URI).build();

        try {
            return request.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Playlist getPlaylist(String userId, String playlistId) {
        // Create a request object for the type of request you want to make
        PlaylistRequest request = api.getPlaylist(userId, playlistId).build();

        try {
            return request.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *
     * Return a random track in a playlist.
     *
     * @param userId The ID of the user that the playlist belongs to.
     * @param playlistURI The URI link to the playlist.
     * @return A track from the playlist.
     */
    @Override
    public Track getRandomTrackFromPlaylist(String userId ,String playlistURI) {
        PlaylistRequest request = api.getPlaylist(userId, playlistURI).build();

        try {
            // Get the playlist.
            Playlist playlist = request.get();

            // Convert it to a list to be able to perform operations.
            List<PlaylistTrack> tracks = playlist.getTracks().getItems();

            // Get a random number based on the length of the list.
            int randomNum = ThreadLocalRandom.current().nextInt(0, tracks.size());

            // Select the track and return it.
            return tracks.get(randomNum).getTrack();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * Generates the authentication URL which the user must visit to accept this application to access their spotify data.
     *
     * Code is written by the creators of the wrapper.
     * See class description for more information
     *
     * @return returns the URL that needs to be visited
     */
    @Override
    public String getAuthenticationURL() {
        // Start a server yo handle the incoming spotify request
        SimpleServer server = new SimpleServer(this);

        final String clientId = "037636c06b1c4348b69fa5646304de02";
        final String clientSecret = "2b9d9f8c3a8248c19d8064e3e1ee7bed";
        final String redirectURI = "http://localhost:9000/spotify";

        api = Api.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .redirectURI(redirectURI)
                .build();

        final List<String> scopes = Arrays.asList("user-library-read", "playlist-read-private");
        String state = "<place_holder>";

        return api.createAuthorizeURL(scopes, state);
    }

    /**
     * Get an access token from the clients authorisation.
     *
     * Code is written by the creators of the wrapper.
     * See class description for more information
     *
     * @param code The code return by spotify's authorisation URL.
     */
    public void authoriseCredentials(String code) {

        /* Make a token request. Asynchronous requests are made with the .getAsync method and synchronous requests
        * are made with the .get method. This holds for all type of requests. */
        final SettableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = api.authorizationCodeGrant(code).build().getAsync();

        /* Add callbacks to handle success and failure */
        Futures.addCallback(authorizationCodeCredentialsFuture, new FutureCallback<AuthorizationCodeCredentials>() {
            @Override
            public void onSuccess(AuthorizationCodeCredentials authorizationCodeCredentials) {
                /* The tokens were retrieved successfully! */
                System.out.println("Successfully retrieved an access token! " + authorizationCodeCredentials.getAccessToken());
                System.out.println("The access token expires in " + authorizationCodeCredentials.getExpiresIn() + " seconds");
                System.out.println("Luckily, I can refresh it using this refresh token! " +     authorizationCodeCredentials.getRefreshToken());

                /* Set the access token and refresh token so that they are used whenever needed */
                api.setAccessToken(authorizationCodeCredentials.getAccessToken());
                api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            }

            @Override
            public void onFailure(Throwable throwable) {
            /* Let's say that the client id is invalid, or the code has been used more than once,
            * the request will fail. Why it fails is written in the throwable's message. */
                System.out.println(throwable);
            }
        });
    }
}
