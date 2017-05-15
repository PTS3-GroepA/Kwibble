package data.context.interfaces;

import models.Player;

/**
 * Created by Bryan on 27-3-2017.
 */
public interface PlayerContextInterface {
    boolean add(Player player);

    boolean login(Player player);

    boolean remove(Player player);
}
