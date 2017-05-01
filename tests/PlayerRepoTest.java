import Data.Context.PlayerMySQLContext;
import Data.Repos.PlayerRepo;
import Models.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Bryan on 3-4-2017.
 */
class PlayerRepoTest {
    @Test
    void add() {
        PlayerRepo repo = new PlayerRepo(new PlayerMySQLContext());
        Player p = new Player(1,"Bryan",10,"b.eggels@student.fontys.nl");

        p.setPassword("Test");
        assertTrue(repo.Add(p));
    }

    @Test
    void login() {
        PlayerRepo repo = new PlayerRepo(new PlayerMySQLContext());
        Player p = new Player(1,"Bryan",10,"b.eggels@student.fontys.nl");
        p.setPassword("Test");

        assertTrue(repo.Login(p));

    }

    @Test
    void remove() {
        PlayerRepo repo = new PlayerRepo(new PlayerMySQLContext());
        Player p = new Player(1,"Bryan",10,"b.eggels@student.fontys.nl");


        assertTrue(repo.Remove(p));
    }

}