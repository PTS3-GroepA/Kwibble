package Data.Context.Interfaces;

import Models.Player;

/**
 * Created by Bryan on 27-3-2017.
 */
public interface PlayerContextInterface {
    public boolean Add(Player player);
    public boolean Login(Player player);
    public boolean Remove(Player player);
}
