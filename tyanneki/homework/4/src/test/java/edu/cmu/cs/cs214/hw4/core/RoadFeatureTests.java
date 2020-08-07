package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class RoadFeatureTests {
    RoadFeature roadFeature;
    RoadFeature completedRoad;
    Tile startingTile;

    @Before
    public void setup() {
        startingTile = Tile.readInStartingTile();
        ArrayList<Segment> oneRoad = new ArrayList<>();
        oneRoad.add(new Segment("road"));
        roadFeature = new RoadFeature(oneRoad);
        ArrayList<Segment> completedRoadList = new ArrayList<>();
        completedRoadList.add(new Segment("roadEnd"));
        completedRoadList.add(new Segment("roadEnd"));
        completedRoad = new RoadFeature(completedRoadList);
        completedRoad.setCompleted();

    }

    @Test(expected = IllegalStateException.class)
    public void testCheckCompletionStatus() {
        assertFalse(roadFeature.checkCompletionStatus(null, false));
        assertTrue(completedRoad.checkCompletionStatus(null, false));
        ArrayList<Segment> segs = completedRoad.segmentsInFeature();
        segs.add(new Segment("roadEnd"));
        RoadFeature feat = new RoadFeature(segs);
        feat.checkCompletionStatus(null, false);
    }

    @Test
    public void testCalculateScore() {
        Player player1 = new Player(1);
        assertEquals(roadFeature.calculateScore(player1, 2, true), 0);
        assertEquals(roadFeature.calculateScore(player1, 2, false), 0);
        roadFeature.playFollowerOnFeature(player1);
        assertEquals(roadFeature.calculateScore(player1, 2, true), 2);
        assertEquals(roadFeature.calculateScore(player1, 2, false), 0);
        assertEquals(completedRoad.calculateScore(player1, 5, false), 0);
        completedRoad.playFollowerOnFeature(player1);
        assertEquals(completedRoad.calculateScore(player1, 5, false), 5);
        completedRoad.playFollowerOnFeature(player1);
        assertEquals(completedRoad.calculateScore(player1, 5, true), 5);

    }
}
