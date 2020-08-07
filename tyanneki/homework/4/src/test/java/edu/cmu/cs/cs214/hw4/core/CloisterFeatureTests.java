package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CloisterFeatureTests {
    CloisterFeature cloister;
    CloisterFeature compCloister;
    Tile startingTile;

    @Before
    public void setup() {
        startingTile = Tile.readInStartingTile();
        ArrayList<Segment> one = new ArrayList<>();
        one.add(new Segment("cloister"));
        cloister = new CloisterFeature(one);
        ArrayList<Segment> completedList = new ArrayList<>();
        completedList.add(new Segment("cloister"));
        compCloister = new CloisterFeature(completedList);
        compCloister.setCompleted();

    }

    @Test(expected = IllegalStateException.class)
    public void testCheckCompletionStatus() {
        ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles = new ArrayList<>();
        Pair<Integer, Integer> p = new Pair<>(1, 2);
        Pair<Tile, Pair<Integer, Integer>> pairs = new Pair<>(startingTile, p);
        tiles.add(pairs);
        assertFalse(cloister.checkCompletionStatus(tiles, false));
        assertFalse(compCloister.checkCompletionStatus(tiles, false));
        assertTrue(compCloister.checkCompletionStatus(tiles, true));
        tiles.add(pairs);
        compCloister.checkCompletionStatus(tiles, false);
    }

    @Test
    public void testCalculateScore() {
        Player player1 = new Player(1);
        assertEquals(cloister.calculateScore(player1, 2, true), 0);
        assertEquals(cloister.calculateScore(player1, 1, false), 0);
        cloister.playFollowerOnFeature(player1);
        assertEquals(cloister.calculateScore(player1, 1, true), 1);
        compCloister.playFollowerOnFeature(player1);
        assertEquals(cloister.calculateScore(player1, 1, false), 0);
        assertEquals(compCloister.calculateScore(player1, 1, false), 9);
        compCloister.playFollowerOnFeature(player1);
        assertEquals(compCloister.calculateScore(player1, 5, false), 9);
        compCloister.playFollowerOnFeature(player1);
        assertEquals(compCloister.calculateScore(player1, 5, true), 9);

    }
}
