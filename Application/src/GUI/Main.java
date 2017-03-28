package GUI;

import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.TrackRequest;
import com.wrapper.spotify.models.SimpleArtist;
import com.wrapper.spotify.models.Track;
import java.net.URL;
import javax.sound.sampled.*;
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

            play();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not get albums.");
        }
    }

    static public void play() {
        AudioInputStream din = null;
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new URL("https://p.scdn.co/mp3-preview/32994bd561930876c195f3cee24dcef221fcce61?cid=null"));
            AudioFormat baseFormat = in.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
                    false);
            din = AudioSystem.getAudioInputStream(decodedFormat, in);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            if(line != null) {
                line.open(decodedFormat);
                byte[] data = new byte[4096];
                // Start
                line.start();

                int nBytesRead;
                while ((nBytesRead = din.read(data, 0, data.length)) != -1) {
                    line.write(data, 0, nBytesRead);
                }
                // Stop
                line.drain();
                line.stop();
                line.close();
                din.close();
            }

        }
        catch(Exception e) {
            e.printStackTrace();
        }
        finally {
            if(din != null) {
                try { din.close(); } catch(IOException e) { }
            }
        }
    }

}
