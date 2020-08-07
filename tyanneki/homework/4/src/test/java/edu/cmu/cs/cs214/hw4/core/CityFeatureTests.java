package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CityFeatureTests {
    CityFeature city;
    CityFeature cityPennant;
    Tile startingTile;

    @Before
    public void setup() {
        startingTile = Tile.readInStartingTile();
        ArrayList<Segment> one = new ArrayList<>();
        one.add(new Segment("city"));
        city = new CityFeature(one);
        ArrayList<Segment> completedList = new ArrayList<>();
        completedList.add(new Segment("cityPennant"));
        cityPennant = new CityFeature(completedList);
        cityPennant.setCompleted();

    }

    @Test
    public void testCheckCompletionStatus() {
        ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles = new ArrayList<>();
        Pair<Integer, Integer> p = new Pair<>(1, 2);
        Pair<Tile, Pair<Integer, Integer>> pairs = new Pair<>(startingTile, p);
        tiles.add(pairs);
        assertFalse(city.checkCompletionStatus(tiles, false));
        Tile newS = Tile.readInStartingTile();
        newS.rotateTile();
        newS.rotateTile();
        Pair<Tile, Pair<Integer, Integer>> newStart = new Pair<>(newS, new Pair<>(1,3));
        tiles.add(newStart);

        assertTrue(city.checkCompletionStatus(tiles, false));
        assertTrue(city.checkCompletionStatus(tiles, true));

        tiles = new ArrayList<>();
        p = new Pair<>(1, 2);
        startingTile.rotateTile();
        pairs = new Pair<>(startingTile, p);
        tiles.add(pairs);
        assertFalse(city.checkCompletionStatus(tiles, false));
        newS = Tile.readInStartingTile();
        newS.rotateTile();
        newS.rotateTile();
        newS.rotateTile();
        newStart = new Pair<>(newS, new Pair<>(2,2));
        tiles.add(newStart);

        assertTrue(city.checkCompletionStatus(tiles, false));
        assertTrue(city.checkCompletionStatus(tiles, true));
    }

    @Test
    public void testCalculateScore() {
        Player player1 = new Player(1);
        assertEquals(city.calculateScore(player1, 2, false), 0);
        assertEquals(cityPennant.calculateScore(player1, 1, false), 0);
        city.playFollowerOnFeature(player1);
        assertEquals(city.calculateScore(player1, 1, false), 0);
        cityPennant.playFollowerOnFeature(player1);
        assertEquals(cityPennant.calculateScore(player1, 1, false), 4);
        cityPennant.playFollowerOnFeature(player1);
        assertEquals(cityPennant.calculateScore(player1, 5, true), 12);
        cityPennant.playFollowerOnFeature(player1);
        assertEquals(city.calculateScore(player1, 7, true), 7);




    }
}
