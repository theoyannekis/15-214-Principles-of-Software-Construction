package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CarcassonneTests {
    private Carcassonne carcassonne;
    @Before
    public void setup() {
        carcassonne = new Carcassonne(2);
    }

    @Test
    public void testInitialization() {
        assertEquals(carcassonne.getBoard().getBoard().size(), carcassonne.getBoard().BOARDMIDINDEX * 2 + 1);
        assertEquals(carcassonne.getTileStack().getTileStack().size(), 70);
        assertEquals(carcassonne.getPlayers().size(), 2);
        assertEquals(carcassonne.getActivePlayer(), carcassonne.getPlayers().get(0));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooFewPlayers() {
        Carcassonne c = new Carcassonne(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTooManyPlayers() {
        Carcassonne c = new Carcassonne(6);
    }

    @Test
    public void testChangePlayerTurn() {
        carcassonne.changePlayerTurn();
        assertEquals(carcassonne.getActivePlayer(), carcassonne.getPlayerByNumber(2));
        carcassonne.changePlayerTurn();
        assertEquals(carcassonne.getActivePlayer(), carcassonne.getPlayerByNumber(1));
    }

    @Test
    public void testDrawTile() {
        Tile tile = carcassonne.drawTile();
        assertFalse(carcassonne.getTileStack().getTileStack().contains(tile));
        assertEquals(carcassonne.getTileStack().getTileStack().size(), 69);
    }

//    @Test
//    public void testMakeMove() {
//        Tile tile = carcassonne.drawTile();
//        carcassonne.makeMove(tile, carcassonne.getBoard().BOARDMIDINDEX, carcassonne.getBoard().BOARDMIDINDEX - 1);
//        assertEquals(carcassonne.getBoard().getTileAtLocation(carcassonne.getBoard().BOARDMIDINDEX,
//                carcassonne.getBoard().BOARDMIDINDEX - 1), tile);
//        assertEquals(carcassonne.getBoard().getFeatures().size(), 6);
//
//    }

//    @Test
//    public void testGameplay() {
//
//        Tile tile = carcassonne.getCurrentTile();
//        carcassonne.rotateTile(tile, 0);
//        carcassonne.makeMove(tile, carcassonne.getBoard().BOARDMIDINDEX, carcassonne.getBoard().BOARDMIDINDEX - 1);
//        //play follower on road below cloister
//        carcassonne.playFollower(carcassonne.getBoard().getFeatures().get(5));
//        carcassonne.changePlayerTurn();
//        assertEquals(carcassonne.getPlayerByNumber(1).getScore(), Integer.valueOf(0));
//        assertEquals(carcassonne.getPlayerByNumber(2).getScore(), Integer.valueOf(0));
//
//        Tile tile2 = carcassonne.drawTile();
//        carcassonne.rotateTile(tile2, 2);
//        carcassonne.makeMove(tile2, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 1);
//        carcassonne.changePlayerTurn();
//        assertEquals(carcassonne.getPlayerByNumber(1).getScore(), Integer.valueOf(2));
//        assertEquals(carcassonne.getPlayerByNumber(2).getScore(), Integer.valueOf(0));
//        assertEquals(carcassonne.getBoard().getTileAtLocation(carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 1), tile2);
//        assertEquals(carcassonne.getBoard().getTileAtLocation(carcassonne.getBoard().BOARDMIDINDEX,
//                carcassonne.getBoard().BOARDMIDINDEX - 1), tile);
//
//        Tile tile3 = carcassonne.drawTile();
//        carcassonne.rotateTile(tile3, 0);
//        carcassonne.makeMove(tile3, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 2);
//        carcassonne.changePlayerTurn();
//
//
//        Tile tile4 = carcassonne.drawTile();
//        carcassonne.rotateTile(tile4, 0);
//        carcassonne.makeMove(tile4, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 3);
//        carcassonne.changePlayerTurn();
//
//
//        Tile tile5 = carcassonne.drawTile();
//        carcassonne.rotateTile(tile5, 0);
//        carcassonne.makeMove(tile5, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 4);
//        carcassonne.changePlayerTurn();
//
//
//        Tile tile6 = carcassonne.drawTile();
//        assertEquals(tile6.constructTileFeatures().get(0).featureType(), "field");
//        carcassonne.rotateTile(tile6, 0);
//        carcassonne.makeMove(tile6, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX - 5);
//
//        Tile tile7 = carcassonne.drawTile();
//        carcassonne.rotateTile(tile7, 0);
//        carcassonne.makeMove(tile7, carcassonne.getBoard().BOARDMIDINDEX,
//                carcassonne.getBoard().BOARDMIDINDEX + 1);
//        //There's only one city feature currently and I want to play a follower on it
//        for (Feature feat : carcassonne.getBoard().getFeatures()) {
//            if (feat.featureType().equals("city")) {
//                feat.playFollowerOnFeature(carcassonne.getActivePlayer());
//            }
//        }
//        carcassonne.changePlayerTurn();
//
//
//        assertEquals(carcassonne.getPlayerByNumber(2).getScore(), Integer.valueOf(0));
//        assertEquals(carcassonne.getPlayerByNumber(1).getScore(), Integer.valueOf(2));
//
//        Tile tile8 = carcassonne.drawTile();
//        assertTrue(tile8.identicalTile(Tile.readInStartingTile()));
//        carcassonne.rotateTile(tile8, 1);
//        carcassonne.makeMove(tile8, carcassonne.getBoard().BOARDMIDINDEX - 1,
//                carcassonne.getBoard().BOARDMIDINDEX + 1);
//        carcassonne.changePlayerTurn();
//
//
//        Tile tile9 = carcassonne.drawTile();
//        assertTrue(tile9.identicalTile(Tile.readInStartingTile()));
//        carcassonne.rotateTile(tile9, 3);
//        carcassonne.makeMove(tile9, carcassonne.getBoard().BOARDMIDINDEX + 1,
//                carcassonne.getBoard().BOARDMIDINDEX + 1);
//        int numFeats = carcassonne.getBoard().getFeatures().size();
//        carcassonne.playFollower(carcassonne.getBoard().getFeatures().get(numFeats - 4));
//        carcassonne.changePlayerTurn();
//
//        Tile tile10 = carcassonne.drawTile();
//        assertTrue(tile10.identicalTile(Tile.readInStartingTile()));
//        carcassonne.rotateTile(tile10, 2);
//        carcassonne.makeMove(tile10, carcassonne.getBoard().BOARDMIDINDEX,
//                carcassonne.getBoard().BOARDMIDINDEX + 2);
//        carcassonne.changePlayerTurn();
//
//
//        assertEquals(carcassonne.getPlayerByNumber(2).getScore(), Integer.valueOf(12));
//        assertEquals(carcassonne.getPlayerByNumber(1).getScore(), Integer.valueOf(2));
//
//        //When the game ends the farmer needs to be scored
//        carcassonne.endGame();
//
//        assertEquals(carcassonne.getPlayerByNumber(2).getScore(), Integer.valueOf(15));
//        assertEquals(carcassonne.getPlayerByNumber(1).getScore(), Integer.valueOf(2));
//
//    }

}
