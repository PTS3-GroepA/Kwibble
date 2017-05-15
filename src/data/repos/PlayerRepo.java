package data.repos;

import data.context.interfaces.PlayerContextInterface;
import models.Player;

/**
 * Created by Bryan on 27-3-2017.
 */
public class PlayerRepo implements PlayerContextInterface {


    PlayerContextInterface context;


    public PlayerRepo(PlayerContextInterface context) {
        this.context = context;
    }

    @Override
    public boolean Add(Player player) {
        return context.Add(player);
    }

    @Override
    public boolean Login(Player player) {
        return context.Login(player);
    }

    @Override
    public boolean Remove(Player player) {
        return context.Remove(player);
    }
}
