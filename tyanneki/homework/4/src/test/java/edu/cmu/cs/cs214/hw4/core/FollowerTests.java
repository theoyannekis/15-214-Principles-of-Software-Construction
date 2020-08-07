package edu.cmu.cs.cs214.hw4.core;

import org.junit.Test;
import static org.junit.Assert.*;



public class FollowerTests {
    @Test
    public void testGetPlayer() {
        Player player1 = new Player(1);
        Follower follower1 = new Follower(player1);
        assertEquals(follower1.getPlayer(), player1);
        Player player2 = new Player(2);
        Follower follower2 = new Follower(player2);
        assertEquals(follower2.getPlayer(), player2);
        assertNotEquals(follower1.getPlayer(), follower2.getPlayer());
    }
}
