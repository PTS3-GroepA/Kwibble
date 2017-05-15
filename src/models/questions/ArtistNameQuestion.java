package models.questions;

import com.wrapper.spotify.models.Artist;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;
import models.answer.TextAnswer;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by dennisvermeulen on 10-04-17.
 * <p>
 * Question where the answer type will be artists.
 */
public class ArtistNameQuestion extends Question {


    public ArtistNameQuestion(String questionString, int maxAnswerTime, String source, boolean showAlbumArt, int score) {
        super(questionString, maxAnswerTime, source, showAlbumArt, score);
    }

    public ArtistNameQuestion(String questionString, boolean showAlbumArt, String answerType) {
        super(questionString, showAlbumArt, answerType);
    }

    @Override
    public void generateAnswers() {
        Track track = musicRepo.getTrack(source);
        // Add the correct answer.
        SimpleArtist artist = track.getArtists().get(0);
        addAnswer(new TextAnswer(artist.getName(), true));

        // Add 3 related artist as fake answers
        List<Artist> related = musicRepo.getRelatedArtist(artist.getId());
        for (int j = 0; j < 3; j++) {
            int randomNum = ThreadLocalRandom.current().nextInt(0, related.size());
            addAnswer(new TextAnswer(related.get(randomNum).getName(), false));
        }

        System.out.println(this);
    }
}
