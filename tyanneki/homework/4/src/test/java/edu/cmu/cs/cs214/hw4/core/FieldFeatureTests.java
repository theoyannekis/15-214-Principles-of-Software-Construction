package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FieldFeatureTests {
    FieldFeature field;
    Tile startingTile;

    @Before
    public void setup() {
        startingTile = Tile.readInStartingTile();
        ArrayList<Segment> one = new ArrayList<>();
        one.add(new Segment("field"));
        field = new FieldFeature(one);

    }

    @Test
    public void testCheckCompletionStatus() {
        assertFalse(field.checkCompletionStatus(null, false));
        assertFalse(field.checkCompletionStatus(null, true));
    }

    @Test
    public void testCalculateScore() {
        Player player1 = new Player(1);
        assertEquals(field.calculateScore(player1, 2, true), 0);
        assertEquals(field.calculateScore(player1, 1, false), 0);
        field.playFollowerOnFeature(player1);
        assertEquals(field.calculateScore(player1, 1, false), 0);
        assertEquals(field.calculateScore(player1, 1, true), 3);


    }
}
