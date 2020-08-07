package edu.cmu.cs.cs214.hw4.core;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;



public class TileStackTests {
    TileStack tileStack;

    @Before
    public void setup() {
        tileStack = new TileStack();
    }

    @Test
    public void testTileStack() {
        assertEquals(tileStack.getTileStack().size(), 71);
        boolean dummyFlag = false;
        for (Tile tile : tileStack.getTileStack()) {
            if (tile.isDummyTile()) {
                dummyFlag = true;
            }
        }
        assertFalse(dummyFlag);
        TileStack anotherStack = new TileStack();
        boolean matchingStacksFlag = true;
        for (Tile tile : tileStack.getTileStack()) {
            boolean foundTile = false;
            for (Tile otherTile : anotherStack.getTileStack()) {
                if (otherTile.identicalTile(tile)) {
                    foundTile = true;
                }
            }
            if (!foundTile) {
                matchingStacksFlag = false;
            }
        }
        assertTrue(matchingStacksFlag);
        //Check for duplicate tiles. Shouldn't be any
        Set<Tile> set = new HashSet<>(tileStack.getTileStack());
        assertEquals(set.size(), tileStack.getTileStack().size());

    }

    @Test
    public void testPickTile() {
        Tile tile = tileStack.pickTile();
        assertEquals(tileStack.getTileStack().size(), 70);
        Tile tile2 = tileStack.pickTile();
        assertEquals(tileStack.getTileStack().size(), 69);
        assertFalse(tile.isDummyTile());
        assertFalse(tile2.isDummyTile());
    }

    @Test
    public void testSetTileStack() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tileStack.setTileStack(tiles);
        assertEquals(tileStack.getTileStack(), tiles);

    }

    @Test
    public void testGetTileStack() {
        ArrayList<Tile> tiles = tileStack.getTileStack();
        assertEquals(71, tiles.size());

    }

    @Test
    public void testEmpty() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tileStack.setTileStack(tiles);
        assertTrue(tileStack.empty());
    }

    @Test
    public void testDiscardTile() {
        ArrayList<Tile> tiles = tileStack.getTileStack();
        Tile firstTile = tiles.get(0);
        tileStack.discardTile();
        assertEquals(tileStack.getTileStack().size(), 70);
        assertFalse(tileStack.getTileStack().contains(firstTile));
    }

    @Test
    public void testShuffleDeck() {
        ArrayList<Tile> tiles = tileStack.getTileStack();
        tileStack.shuffleStack();
        assertEquals(tileStack.getTileStack(), tiles);
    }
}
