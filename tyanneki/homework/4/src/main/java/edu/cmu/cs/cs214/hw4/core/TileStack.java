package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Collections;

import static edu.cmu.cs.cs214.hw4.core.TileInputBean.parse;

 class TileStack {
    private ArrayList<Tile> tileStack = new ArrayList<>();

    TileStack () {
        TileInputBean tileInput = parse("src/main/resources/config.yml");
        //Get the tile input and store it as an arrayList of TileInputBean
        ArrayList<TileInputBean.TileStackBean> tileStack = new ArrayList<>(Arrays.asList(tileInput.getTileStack()));
        //Convert that list into a list of Maps mapping ("northMid", "field")... for each tile
        ArrayList<Map<String, String>> mapList = tileInput.makeTileMapStack(tileStack);
        //Finally create a tile for every single map in the array to get our list of tiles.
        for (Map<String, String> map : mapList) {
            this.tileStack.add(new Tile(map));
        }



    }

    /**
     * get the tile from the top of the tileStack. destructively removes this tile from the stack
     * @return the tile from the top of the stack
     */
    Tile pickTile() {
        return tileStack.remove(0);
    }

    /**
     * check if the tileStack is empty
     * @return true if there are no tiles in the stack. false if not
     */
    boolean empty() {
        return this.tileStack.size() == 0;
    }

    /**
     * get the list representation of the tileStack
     * @return tileStack list
     */
    ArrayList<Tile> getTileStack() { return tileStack; }

    /**
     * set tileStack to be something else
     */
    void setTileStack(ArrayList<Tile> tileStack) { this.tileStack = tileStack; }

    /**
     * shuffle the tiles in the tileStack. This should be done at the start of the game
     */
    void shuffleStack() {
        Collections.shuffle(this.tileStack);
    }

    /**
     * discard a tile from the stack. remove it.
     */
    void discardTile() {
        tileStack.remove(0);
    }

    Integer tilesLeft() { return this.tileStack.size(); }

}
