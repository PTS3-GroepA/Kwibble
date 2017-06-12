package models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by dande on 15-4-2017.
 */
class PlayerTest {
    Player p = new Player("testPlayer", 10, "testplayer@email.com", "testPassword");
    Player p1 = new Player(1, "Testplayer1", 20, "Player@email.com");


    @Test
    void getId() {
        assertEquals(1, p1.getId());
        //De id waarde word niet geinstantieerd bij deze constructor;
        assertEquals(0, p.getId());
    }

    @Test
    void setId() {
        p1.setId(2);
        //Deze test is afhankelijk van de get id methode dus ik weet niet of hij goed is
        assertEquals(2, p1.getId());
    }

    @Test
    void getName() {
        assertEquals("testPlayer", p.getName());
        assertEquals("Testplayer1", p1.getName());
    }

    @Test
    void setName() {
        p1.setName("setNameTest");
        assertEquals("setNameTest", p1.getName());
    }

    @Test
    void getScore() {
        assertEquals(10, p.getScore());
        assertEquals(20, p1.getScore());
    }

    @Test
    void setScore() {
        p1.setScore(100);
        assertEquals(100, p1.getScore());
    }

    @Test
    void getEmail() {
        assertEquals("testplayer@email.com", p.getEmail());
        assertEquals("Player@email.com", p1.getEmail());
    }

    @Test
    void setEmail() {
        p1.setEmail("setemail@test.com");
        assertEquals("setemail@test.com", p1.getEmail());
    }

    @Test
    void getPassword() {
        assertEquals("testPassword", p.getPassword());
    }

    @Test
    void setPassword() {
        p.setPassword("setPasswordTest");
        assertEquals("setPasswordTest", p.getPassword());
    }

}