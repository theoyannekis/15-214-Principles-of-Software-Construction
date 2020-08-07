package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.junit.Assert.*;

public class BoardsTests {
    private Board board;
    private Tile startingTile;
    @Before
    public void setup() {
        board = new Board();
        startingTile = Tile.readInStartingTile();
    }

    @Test
    public void testBoard() {
        Tile midTile = board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX);
        assertTrue(midTile.identicalTile(startingTile));
    }

    @Test
    public void testGetFeatures() {
        ArrayList<Feature> feats = board.getFeatures();
        ArrayList<Feature> start = startingTile.constructTileFeatures();
        assertEquals(feats.size(), start.size());
        boolean typeFlag = true;
        for (int i = 0; i < feats.size(); i++) {
            if (!feats.get(i).featureType().equals(start.get(i).featureType())) {
                typeFlag = false;
            }
        }
        assertTrue(typeFlag);
    }

    @Test
    public void testNumSurroundingPlayedTiles() {
        assertEquals(board.numSurroundingPlayedTiles(board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX))
                , 0);
        Tile belowTile = board.getTileAtLocation(board.BOARDMIDINDEX - 1, board.BOARDMIDINDEX);
        Pair<Integer, Integer> p = new Pair<>(board.BOARDMIDINDEX - 1, board.BOARDMIDINDEX);
        Pair<Integer, Integer> pi = board.locationOnBoard(belowTile);
        assertEquals(p.getKey(), pi.getKey());
        assertEquals(p.getValue(), pi.getValue());
        assertEquals(board.numSurroundingPlayedTiles(belowTile), 1);

    }

    @Test
    public void testCalculatePlayerScores() {
        ArrayList<Feature> feats = board.getFeatures();
        Player player1 = new Player(1);
        feats.get(1).playFollowerOnFeature(player1);
        feats.get(3).playFollowerOnFeature(player1);
        board.calculatePlayerScores(false);
        assertEquals(player1.getScore(), Integer.valueOf(0));
        board.calculatePlayerScores(true);
        assertEquals(player1.getScore(), Integer.valueOf(2));
        feats.get(1).playFollowerOnFeature(player1);
        feats.get(3).playFollowerOnFeature(player1);
        feats.get(3).setCompleted();
        board.calculatePlayerScores(false);
        assertEquals(player1.getScore(), Integer.valueOf(4));

    }

    @Test
    public void testUpdateAllFeatures() {
        Tile flip = Tile.readInStartingTile();
        flip.rotateTile();
        flip.rotateTile();
        board.placeTile(board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1, flip);
        assertEquals(board.getFeatures().size(), 8);
        boolean completeFlag = false;
        for (Feature feat : board.getFeatures()) {
            if (feat.isComplete()) {
                completeFlag = true;
            }
        }
        assertFalse(completeFlag);
        board.updateAllFeaturesCompletion();
        //City should be only complete one
        completeFlag = false;
        boolean cityCompleteFlag = false;
        for (Feature feat : board.getFeatures()) {
            if (feat.featureType().equals("city")) {
                cityCompleteFlag = true;
                break;
            }
            if (feat.isComplete()) {
                completeFlag = true;
            }
        }
        assertFalse(completeFlag);
        assertTrue(cityCompleteFlag);

    }

    @Test
    public void testFindTilesOfFeature() {
        Tile flip = Tile.readInStartingTile();
        flip.rotateTile();
        flip.rotateTile();
        board.placeTile(board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1, flip);
        board.updateAllSurroundingFeatures(flip, board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1);
        board.updateAllFeaturesCompletion();
        ArrayList<Pair<Tile, Pair<Integer, Integer>>> pairList = board.findTilesOfFeature(board.getFeatures().get(0));
        assertEquals(pairList.get(0).getKey(), board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX));
        pairList = board.findTilesOfFeature(board.getFeatures().get(1));
        assertEquals(pairList.get(0).getKey(), board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX));
        pairList = board.findTilesOfFeature(board.getFeatures().get(2));
        assertEquals(pairList.get(0).getKey(), board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX));
        pairList = board.findTilesOfFeature(board.getFeatures().get(3));
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(pairList.get(0).getKey());
        tiles.add(pairList.get(1).getKey());
        assertTrue(tiles.contains(board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX)));
        assertTrue(tiles.contains(board.getTileAtLocation(board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1)));


    }
    @Test
    public void testFarmsSupplyingCities() {
        Tile flip = Tile.readInStartingTile();
        flip.rotateTile();
        flip.rotateTile();
        board.placeTile(board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1, flip);
        board.updateAllSurroundingFeatures(flip, board.BOARDMIDINDEX, board.BOARDMIDINDEX + 1);
        board.updateAllFeaturesCompletion();
        Map<Feature, Integer> map = board.farmsSupplyingCities();
        assertEquals(map.size(), 2);
        assertTrue(map.keySet().contains(board.getFeatures().get(2)));
        assertEquals(map.get(board.getFeatures().get(2)), new Integer(1));
        assertTrue(map.keySet().contains(board.getFeatures().get(4)));
        assertEquals(map.get(board.getFeatures().get(4)), new Integer(1));

    }

    @Test
    public void placeTile() {
        Tile t = Tile.readInStartingTile();
        board.placeTile(board.BOARDMIDINDEX + 1, board.BOARDMIDINDEX, t);
        assertEquals(board.getTileAtLocation(board.BOARDMIDINDEX + 1, board.BOARDMIDINDEX), t);

    }

    @Test(expected = IllegalArgumentException.class)
    public void placeTileInOccupiedSpot() {
        Tile t = Tile.readInStartingTile();
        board.placeTile(board.BOARDMIDINDEX, board.BOARDMIDINDEX, t);

    }

    @Test(expected = IllegalArgumentException.class)
    public void placeTileInNotMatchingSpot() {
        Tile t = Tile.readInStartingTile();
        t.rotateTile();
        board.placeTile(board.BOARDMIDINDEX - 1, board.BOARDMIDINDEX, t);

    }

    @Test(expected = IllegalArgumentException.class)
    public void placeTileNotAdjacentToOtherTile() {
        Tile t = Tile.readInStartingTile();
        t.rotateTile();
        board.placeTile(board.BOARDMIDINDEX - 2, board.BOARDMIDINDEX, t);

    }
}
