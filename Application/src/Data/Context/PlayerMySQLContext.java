package Data.Context;

import Data.Context.Interfaces.PlayerContextInterface;
import Models.Player;

/**
 * Created by Bryan on 27-3-2017.
 */
public class PlayerMySQLContext implements PlayerContextInterface {


    

    @Override
    public boolean AddUser(Player player) {
        return false;
    }
}
