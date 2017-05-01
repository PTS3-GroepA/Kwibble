package Data.Context.Interfaces;

import Models.Player;

/**
 * Created by Bryan on 27-3-2017.
 */
public interface PlayerContextInterface {
    boolean Add(Player player);
    boolean Login(Player player);
    boolean Remove(Player player);
}
