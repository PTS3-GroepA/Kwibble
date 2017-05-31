package models;

import java.util.Comparator;

/**
 * Created by Max Meijer on 31/05/2017.
 * Fontys University of Applied Sciences, Eindhoven
 */
public class ScoreComparer implements Comparator<Player> {

    @Override
    public int compare(Player o1, Player o2) {
        return Integer.compare(o1.getScore(), o2.getScore());
    }
}
