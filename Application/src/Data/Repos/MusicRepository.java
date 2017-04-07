package Data.Repos;

import Data.Context.Interfaces.MusicContext;
import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.Track;

import java.util.List;

/**
 * Created by Max Meijer on 27/03/2017.
 * Fontys University of Applied Sciences, Eindhoven.
 *
 * This class is used for fetching all music related data.
 * It depends on a context file to locate said data.
 */

public class MusicRepository implements MusicContext {

    private MusicContext context;

    public MusicRepository(MusicContext context) {
        this.context = context;
    }

    @Override
    public Track getTrack(String URI) {
        return context.getTrack(URI);
    }

    @Override
    public Artist getArtist(String URI) {
        return context.getArtist(URI);
    }

    @Override
    public Playlist getPlaylist(String userId, String playlistId) {
        return context.getPlaylist(userId, playlistId);
    }

    @Override
    public String getAuthenticationURL() {
        return context.getAuthenticationURL();
    }

    @Override
    public Track getRandomTrackFromPlaylist(String userId, String playlistURI) {
        return context.getRandomTrackFromPlaylist(userId, playlistURI);
    }

    @Override
    public List<Artist> getRelatedArtist(String ArtistID) {
        return context.getRelatedArtist(ArtistID);
    }
}
