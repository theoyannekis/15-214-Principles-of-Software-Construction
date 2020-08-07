package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;



public class PlayerTests {
    private Player player2;
    private Player player1;

    @Before
    public void setup() {
        player1 = new Player(1);
        player2 = new Player(2);
    }

    @Test
    public void testGetScore() {
        assertEquals(player1.getScore(), Integer.valueOf(0));
        player1.incrementScore(5);
        assertEquals(player1.getScore(), Integer.valueOf(5));
        assertNotEquals(player1.getScore(), player2.getScore());
    }

    @Test
    public void testIncrementScore() {
        player1.incrementScore(5);
        assertEquals(player1.getScore(), Integer.valueOf(5));
        player1.incrementScore(10);
        assertEquals(player1.getScore(), Integer.valueOf(15));
    }

    @Test
    public void testGetName() {
        String res = "Player 1";
        assertEquals(res, player1.getName());
        res = "Player 2";
        assertEquals(res, player2.getName());
    }

    @Test
    public void testAddPlayerFollower() {
        Follower follower = new Follower(player1);
        assertEquals(player1.followersInPlay(), 0);
        player1.addPlayedFollower(follower);
        assertEquals(player1.followersInPlay(), 1);
        assertEquals(player2.followersInPlay(), 0);
    }

    @Test
    public void testFollowersInPlay() {
        Follower follower = new Follower(player1);
        assertEquals(player1.followersInPlay(), 0);
        player1.addPlayedFollower(follower);
        assertEquals(player1.followersInPlay(), 1);
        player2.addPlayedFollower(new Follower(player2));
        assertEquals(player1.followersInPlay(), 1);
        assertEquals(player2.followersInPlay(), 1);
    }

    @Test
    public void testReturnFollower() {
        Follower follower = new Follower(player1);
        Follower follower2 = new Follower(player1);
        assertEquals(player1.followersInPlay(), 0);
        player1.addPlayedFollower(follower);
        assertEquals(player1.followersInPlay(), 1);
        player1.addPlayedFollower(follower2);
        assertEquals(player1.followersInPlay(), 2);
        player1.returnFollower(follower2);
        assertEquals(player1.followersInPlay(), 1);
        player1.returnFollower(follower);
        assertEquals(player1.followersInPlay(), 0);
    }
}
