package Data.Context.Interfaces;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.Track;

import java.util.List;

/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public interface MusicContext {

    Track getTrack(String URI);

    Artist getArtist(String URI);

    Playlist getPlaylist(String userId, String playlistId);

    String getAuthenticationURL();

    Track getRandomTrackFromPlaylist(String userId, String playlistURI);

    List<Artist> getRelatedArtist(String ArtistID);

    boolean checkAuthorization();

    Api getApi();

    void setApi(Api api);
}
