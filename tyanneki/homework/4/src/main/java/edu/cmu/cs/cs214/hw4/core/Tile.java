package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static edu.cmu.cs.cs214.hw4.core.TileInputBean.parse;
import static java.util.Arrays.asList;

public class Tile {

    private static final int TILEGRIDSIZE = 5;
    private ArrayList<ArrayList<Segment>> tile = new ArrayList<>();
    private String tileName;
    private Integer rotations;

    Tile (Map<String, String> map) {
        //Initialize the grid to represent out tile and fill with null so we can then add correct values
        this.rotations = 0;

        for (int i = 0; i < TILEGRIDSIZE; i++) {
            ArrayList<Segment> row = new ArrayList<>();
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                row.add(j, null);
            }
            this.tile.add(row);
        }
        //Set all the fields in the 2d ArrayList to appropriate values
        this.tileName = map.get("name");

        this.tile.get(0).set(0, new Segment(map.get("cornerNW")));
        this.tile.get(0).set(1, new Segment(map.get("northBot")));
        this.tile.get(0).set(2, new Segment(map.get("northMid")));
        this.tile.get(0).set(3, new Segment(map.get("northTop")));
        this.tile.get(0).set(4, new Segment(map.get("cornerNE")));
        this.tile.get(1).set(0, new Segment(map.get("westTop")));
        this.tile.get(1).set(1, new Segment(map.get("centerNW")));
        this.tile.get(1).set(2, new Segment(map.get("centerN")));
        this.tile.get(1).set(3, new Segment(map.get("centerNE")));
        this.tile.get(1).set(4, new Segment(map.get("eastBot")));
        this.tile.get(2).set(0, new Segment(map.get("westMid")));
        this.tile.get(2).set(1, new Segment(map.get("centerW")));
        this.tile.get(2).set(2, new Segment(map.get("center")));
        this.tile.get(2).set(3, new Segment(map.get("centerE")));
        this.tile.get(2).set(4, new Segment(map.get("eastMid")));
        this.tile.get(3).set(0, new Segment(map.get("westBot")));
        this.tile.get(3).set(1, new Segment(map.get("centerSW")));
        this.tile.get(3).set(2, new Segment(map.get("centerS")));
        this.tile.get(3).set(3, new Segment(map.get("centerSE")));
        this.tile.get(3).set(4, new Segment(map.get("eastTop")));
        this.tile.get(4).set(0, new Segment(map.get("cornerSW")));
        this.tile.get(4).set(1, new Segment(map.get("southTop")));
        this.tile.get(4).set(2, new Segment(map.get("southMid")));
        this.tile.get(4).set(3, new Segment(map.get("southBot")));
        this.tile.get(4).set(4, new Segment(map.get("cornerSE")));

    }

    /**
     * creates a tile filled with all dummy segments
     * @return the tile
     */
    static Tile createDummyTile() {
        TileInputBean tileInput = parse("src/main/resources/dummyTile.yml");
        //Get the tile input and store it as an arrayList of TileInputBean
        ArrayList<TileInputBean.TileStackBean> tileStack = new ArrayList<>(asList(tileInput.getTileStack()));
        //Convert that list into a list of Maps mapping ("northMid", "field")... for each tile
        ArrayList<Map<String, String>> mapList = tileInput.makeTileMapStack(tileStack);
        //Finally create a tile for every single map in the array to get our list of tiles.
        Tile dummyTile = new Tile(mapList.get(0));
        return dummyTile;
    }

    /**
     * checks all segments on a tile to see if they are all "dummy"
     * @return true if all segments on tile are dummy, false if not
     */
    public boolean isDummyTile() {
        return this.tileName.equals("dummy");
    }

    @Override
    public String toString() {
        return this.tile.toString();
    }

    /**
     * Destructively rotates a tile 90 degrees clockwise
     */
    void rotateTile() {
        //temp stores the rotated tile as its built
        ArrayList<ArrayList<Segment>> temp = new ArrayList<>();
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            ArrayList<Segment> row = new ArrayList<>();
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                row.add(j, null);
            }
            temp.add(row);
        }
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                temp.get(i).set(j, this.tile.get(TILEGRIDSIZE - j - 1).get(i));
            }
        }
        this.rotations += 1;

        this.setTile(temp);
    }

    /**
     * gives the non-corner segments on a given side since corners are dummy.
     * @param edge "north", "west", etc...
     * @return the middle segments on that side, not the corner dummy ones
     */
    ArrayList<Segment> getSegmentsOnSide(String edge) {
        ArrayList<Segment> segs = new ArrayList<>();
        switch (edge) {
            case "north":
                segs.add(0, this.tile.get(0).get(1));
                segs.add(1, this.tile.get(0).get(2));
                segs.add(2, this.tile.get(0).get(3));
                return segs;
            case "west":
                segs.add(0, this.tile.get(1).get(0));
                segs.add(1, this.tile.get(2).get(0));
                segs.add(2, this.tile.get(3).get(0));
                return segs;
            case "south":
                segs.add(0, this.tile.get(TILEGRIDSIZE - 1).get(1));
                segs.add(1, this.tile.get(TILEGRIDSIZE - 1).get(2));
                segs.add(2, this.tile.get(TILEGRIDSIZE - 1).get(3));
                return segs;
            default:
                segs.add(0, this.tile.get(1).get(TILEGRIDSIZE - 1));
                segs.add(1, this.tile.get(2).get(TILEGRIDSIZE - 1));
                segs.add(2, this.tile.get(3).get(TILEGRIDSIZE - 1));
                return segs;
        }
    }

    /**
     * checks if the sides of the two given tiles match their segments
     * @param side the side of the object tile to compare to the comparison tile
     * @param comparisonTile the tile to compare to
     * @return true if the segments on the abutting side match, false if not
     */
    boolean sidesMatch(String side, Tile comparisonTile) {
        switch (side) {
            case "north":
                for (int j = 0; j < TILEGRIDSIZE; j++) {
                    Segment tileElem = this.tile.get(0).get(j);
                    Segment compareElem = comparisonTile.getTile().get(TILEGRIDSIZE - 1).get(j);
                    if (!Segment.segmentsMatch(tileElem, compareElem)) {
                        return false;
                    }
                }
                return true;
            case "west":
                for (int i = 0; i < TILEGRIDSIZE; i++) {
                    Segment tileElem = this.tile.get(i).get(0);
                    Segment compareElem = comparisonTile.getTile().get(i).get(TILEGRIDSIZE - 1);
                    if (!Segment.segmentsMatch(tileElem, compareElem)) {
                        return false;
                    }
                }
                return true;
            case "south":
                for (int j = 0; j < TILEGRIDSIZE; j++) {
                    Segment tileElem = this.tile.get(TILEGRIDSIZE - 1).get(j);
                    Segment compareElem = comparisonTile.getTile().get(0).get(j);
                    if (!Segment.segmentsMatch(tileElem, compareElem)) {
                        return false;
                    }
                }
                return true;
            default:
                for (int i = 0; i < TILEGRIDSIZE; i++) {
                    Segment tileElem = this.tile.get(i).get(TILEGRIDSIZE - 1);
                    Segment compareElem = comparisonTile.getTile().get(i).get(0);
                    if (!Segment.segmentsMatch(tileElem, compareElem)) {
                        return false;
                    }
                }
                return true;
        }
    }


    /**
     * read in a tile from a YAML file
     * @param path path to the file
     * @return the Tile object
     */
    static Tile readInTile(String path) {
        TileInputBean tileInput = parse(path);
        //Get the tile input and store it as an arrayList of TileInputBean
        ArrayList<TileInputBean.TileStackBean> tileStack = new ArrayList<>(asList(tileInput.getTileStack()));
        //Convert that list into a list of Maps mapping ("northMid", "field")... for each tile
        ArrayList<Map<String, String>> mapList = tileInput.makeTileMapStack(tileStack);
        //Finally create a tile for every single map in the array to get our list of tiles.
        Tile testTile = new Tile(mapList.get(0));
        return testTile;
    }

    /**
     * read in the starting tile that the game starts with
     * @return the tile the game starts with
     */
    static Tile readInStartingTile() {
        return readInTile("src/main/resources/startingTile.yml");
    }

    /**
     * creates a grid associating every segment in a tile with a letter. Used in algorithm to determine where
     * features start and end
     * @return a list of pairs pairing a segment with a unique letter
     */
    private ArrayList<ArrayList<Pair<Character, Segment>>> createFeatureSearchGrid() {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        ArrayList<ArrayList<Pair<Character, Segment>>> grid = new ArrayList<>();
        //Initialize the grid to represent our segments on the tile. null indicates an empty spot
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            ArrayList<Pair<Character, Segment>> row = new ArrayList<>();
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                Segment elemSeg = this.tile.get(i).get(j);
                Character elemChar = alphabet[(i*TILEGRIDSIZE)+j];
                row.add(j, new Pair<>(elemChar, elemSeg));
            }
            grid.add(row);
        }

        return grid;
    }

    /**
     * prints the tile as a grid
     */
    public void printTile() {
        for (ArrayList<Segment> list: this.tile) {
            System.out.println(list);
        }
        System.out.println();

    }

    /**
     * Destructively modified a given grid to update all Pairs with the findKey to have the key setKey
     * @param grid a grid representing segments on a tile as Pairs
     * @param findKey the key to find in the grid. To be replaced
     * @param setKey the key with which to replace the findKey with
     */
    private static void setAllMatchingKeys(ArrayList<ArrayList<Pair<Character, Segment>>> grid, Character findKey,
                                           Character setKey) {
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                Pair<Character, Segment> elem = grid.get(i).get(j);
                Character elemKey = elem.getKey();
                if (elemKey == findKey) {
                    grid.get(i).set(j, new Pair<>(setKey, elem.getValue()));
                }
            }
        }
    }

    /**
     * compares the keys of neighboring elements. If their segments match it will set both of their keys to be
     * equals to the lower of the two. So it they are both roads and key1 = 'a' and key2 = 'b'. Both keys will
     * be set to 'a'
     * @param elem the element of which we are checking its neighbor
     * @param grid the grid containing all of the elements in the tile
     * @param row the row in the grid of the neighbor
     * @param col the col in the grid of the neighbor
     * @return the grid with the updated keys
     */
    private static ArrayList<ArrayList<Pair<Character, Segment>>> compareNeighbors(Pair<Character, Segment> elem,
                                           ArrayList<ArrayList<Pair<Character, Segment>>> grid, int row, int col) {
        Character elemChar = elem.getKey();
        Segment elemSeg = elem.getValue();
        Pair<Character, Segment> neighbor = grid.get(row).get(col);
        Character neighborChar = neighbor.getKey();
        Segment neighborSeg = neighbor.getValue();
        //If the elem and its neighbor are of the same segment type
        if (elemSeg.sameSegmentType(neighborSeg)) {
            if (elemChar <= neighborChar) {
                setAllMatchingKeys(grid, neighborChar, elemChar);
            }
            else {
                setAllMatchingKeys(grid, elemChar, neighborChar);
            }
        }

        return grid;
    }

    /**
     * Adjusts the key of the corner segments of a tile to match if they are both fields. This accounts for the fact
     * that the dummy node in the corner splits them up. They must be fields because if there are city segments
     * separated by a dummy node in a corner they should remain split. Destructive function
     * @param grid the 2d arraylist representing the tile as a grid of segments.
     */
    private static void adjustFieldCorners(ArrayList<ArrayList<Pair<Character, Segment>>> grid) {
        //NW corner
        Pair<Character, Segment> elemNNW = grid.get(0).get(1);
        Pair<Character, Segment> elemWNW = grid.get(1).get(0);
        if (elemNNW.getValue().sameSegmentType(elemWNW.getValue())
                && elemWNW.getValue().sameSegmentType(new Segment("field"))) {
            setAllMatchingKeys(grid, elemWNW.getKey(), elemNNW.getKey());
        }
        //NE corner
        Pair<Character, Segment> elemNNE = grid.get(0).get(TILEGRIDSIZE - 2);
        Pair<Character, Segment> elemENE = grid.get(1).get(TILEGRIDSIZE - 1);
        if (elemNNE.getValue().sameSegmentType(elemENE.getValue())
                && elemENE.getValue().sameSegmentType(new Segment("field"))) {
            setAllMatchingKeys(grid, elemENE.getKey(), elemNNE.getKey());
        }
        //SW corner
        Pair<Character, Segment> elemSSW = grid.get(TILEGRIDSIZE - 1).get(1);
        Pair<Character, Segment> elemWSW = grid.get(TILEGRIDSIZE - 2).get(0);
        if (elemSSW.getValue().sameSegmentType(elemWSW.getValue())
                && elemWSW.getValue().sameSegmentType(new Segment("field"))) {
            setAllMatchingKeys(grid, elemSSW.getKey(), elemWSW.getKey());
        }
        //SE corner
        Pair<Character, Segment> elemSSE = grid.get(TILEGRIDSIZE - 1).get(TILEGRIDSIZE - 2);
        Pair<Character, Segment> elemESE = grid.get(TILEGRIDSIZE - 2).get(TILEGRIDSIZE - 1);
        if (elemSSE.getValue().sameSegmentType(elemESE.getValue())
                && elemESE.getValue().sameSegmentType(new Segment("field"))) {
            setAllMatchingKeys(grid, elemSSE.getKey(), elemESE.getKey());
        }
    }

    /**
     * use the helpers above to create all the features on a tile.
     * @return a list of all features on the tile
     */
    ArrayList<Feature> constructTileFeatures() {
        ArrayList<ArrayList<Pair<Character, Segment>>> grid = this.createFeatureSearchGrid();
        //Match corner keys if they are both field segments.
        adjustFieldCorners(grid);

        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                Pair<Character, Segment> elem = grid.get(i).get(j);
                //skip any dummy elements
                if (!elem.getValue().isDummySegment()) {
                    //Check its neighbors
                    if (j > 0 && (!grid.get(i).get(j-1).getValue().isDummySegment())) {
                       grid = compareNeighbors(elem, grid, i, j-1);
                    }
                    if (j < TILEGRIDSIZE - 1 && (!grid.get(i).get(j+1).getValue().isDummySegment())) {
                        grid = compareNeighbors(elem, grid, i, j+1);
                    }
                    if (i > 0 && (!grid.get(i-1).get(j).getValue().isDummySegment())) {
                        grid = compareNeighbors(elem, grid, i-1, j);
                    }
                    if (i < TILEGRIDSIZE - 1 && (!grid.get(i+1).get(j).getValue().isDummySegment())) {
                        grid = compareNeighbors(elem, grid, i+1, j);
                    }
                }
            }
        }
        //Now the grid is set up where matching segments will have matching keys in the pairs
        //Loop through and combine all of the elements with matching keys into lists
        ArrayList<ArrayList<Segment>> segLists = new ArrayList<>();
        for (char alpha = 'a'; alpha <= 'z'; alpha++) {
            ArrayList<Segment> list = new ArrayList<>();
            for (int i = 0; i < TILEGRIDSIZE; i++) {
                for (int j = 0; j < TILEGRIDSIZE; j++) {
                    Pair<Character, Segment> elem = grid.get(i).get(j);
                    Character elemChar = elem.getKey();
                    Segment elemSeg = elem.getValue();
                    if (alpha == elemChar) {
                        list.add(elemSeg);
                    }

                }
            }
            segLists.add(list);
        }

        //Now for every list of segments make a feature out of them
        ArrayList<Feature> features = new ArrayList<>();
        for (ArrayList<Segment> list : segLists) {
            if (list.size() > 0) {
                if (!list.get(0).isDummySegment()) {
                    //Now choose which feature to make
                    switch (list.get(0).getType()) {
                        case ("road") : {
                            features.add(new RoadFeature(list));
                            break;
                        }
                        case ("city") : {
                            features.add(new CityFeature(list));
                            break;
                        }
                        case ("cloister") : {
                            features.add(new CloisterFeature(list));
                            break;
                        }
                        default : {
                            features.add(new FieldFeature(list));
                            break;
                        }
                    }
                }
            }
        }
        return features;
    }

    /**
     * helper function which checks if the given list contains all city segments
     * @param segs list of segments
     * @return true if given list is all city segments, false if any are not
     */
    static boolean allCitySegs(ArrayList<Segment> segs) {
        int count = 0;
        for (Segment seg : segs) {
            if (!seg.isDummySegment() && seg.sameSegmentType(new Segment("city"))) {
                count += 1;
            }
        }
        return count == segs.size();
    }

    /**
     * gives all segments on the tile
     * @return list of all segments on the tile
     */
    ArrayList<Segment> getSegmentsInTile() {
        ArrayList<Segment> segs = new ArrayList<>();
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                segs.add(this.tile.get(i).get(j));
            }
        }
        return segs;
    }

//    void playFollowerOnTile(Player player, Board board, int row, int col) {
//        Segment segment = this.tile.get(row).get(col);
//        Feature feature = Board.findFeatureContainingSegment()
//        segment.playFollowerOnSegment(player);
//    }

    /**
     * gives a list of the sides of a tile which have cities on them. So if the north side of a tile is the only
     * one which contains a city feature, then it returns ["north"]
     * @return list of sides of tile which contain city segments
     */
    ArrayList<String> citySides() {
        ArrayList<String> citySides = new ArrayList<>();
        ArrayList<Segment> northSegs = this.getSegmentsOnSide("north");
        if (allCitySegs(northSegs)) {
            citySides.add("north");
        }
        ArrayList<Segment> westSegs = this.getSegmentsOnSide("west");
        if (allCitySegs(westSegs)) {
            citySides.add("west");
        }
        ArrayList<Segment> southSegs = this.getSegmentsOnSide("south");
        if (allCitySegs(southSegs)) {
            citySides.add("south");
        }
        ArrayList<Segment> eastSegs = this.getSegmentsOnSide("east");
        if (allCitySegs(eastSegs)) {
            citySides.add("east");
        }
        return citySides;
    }

    /**
     * check if two tiles have identical segments
     * @param tileToCompare tile to compare the object tile to
     * @return true if they contain segments of the same type in the same spots
     */
    boolean identicalTile(Tile tileToCompare) {
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                Segment tileElem = this.tile.get(i).get(j);
                Segment compareElem = tileToCompare.getTile().get(i).get(j);
                if (!tileElem.sameSegmentType(compareElem)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Find if a particular segment exists on a tile
     * @param segment the segment to be checked for existence on a tile
     * @return true if segment lives on tile, false if not
     */
    boolean containsSegment(Segment segment) {
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                Segment tileElem = this.tile.get(i).get(j);
                if (tileElem.equals(segment)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * get position of a segment in the tile
     * @param segment segment to find position of
     * @return row and column on the tile grid
     */
    Pair<Integer, Integer> getSegPositionInTile(Segment segment) {
        for (int i = 0; i < TILEGRIDSIZE; i++) {
            for (int j = 0; j < TILEGRIDSIZE; j++) {
                if (this.tile.get(i).get(j).equals(segment)) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    /**
     * get a list of the segments adjacent to the given segment on the tile. Doesnt include segments off the edge
     * of the tile
     * @param segment segment to find adjacent segments to
     * @return list of adjacent segments
     */
    ArrayList<Segment> getAdjacentSegments(Segment segment) {
        Pair<Integer, Integer> position = this.getSegPositionInTile(segment);
        if (position == null) {
            return null;
        }
        int row = position.getKey();
        int col = position.getValue();
        ArrayList<Segment> adjSegs = new ArrayList<>();
        if (row > 0) {
            adjSegs.add(this.tile.get(row - 1).get(col));
        }
        if (row < TILEGRIDSIZE - 1) {
            adjSegs.add(this.tile.get(row + 1).get(col));
        }
        if (col > 0) {
            adjSegs.add(this.tile.get(row).get(col - 1));
        }
        if (col < TILEGRIDSIZE - 1) {
            adjSegs.add(this.tile.get(row).get(col + 1));
        }
        if (adjSegs.size() > 0) {
            return adjSegs;
        }
        return null;
    }

    /**
     * gives a set of field segments which are adjacent to the given feature
     * @param feature the feature which we find all field Segments adjacent to it
     * @return set of field segments adjacent to feature
     */
     Set<Segment> getAdjacentFieldSegmentsToFeature(Feature feature) {
        ArrayList<Segment> tileSegs = this.getSegmentsInTile();
        Set<Segment> adjFarmSet = new HashSet<>();
        for (Segment segment : tileSegs) {
            //all segments adjacent to current segment
            ArrayList<Segment> adjSegs = this.getAdjacentSegments(segment);
            //if the current segment is in the feature
            if (adjSegs != null && feature.contains(segment)) {
                for (Segment seg : adjSegs) {
                    //If its a field segment and not part of the feature
                    if (!feature.contains(seg) && seg.sameSegmentType(new Segment("field"))) {
                        adjFarmSet.add(seg);
                    }
                }
            }
        }
        return adjFarmSet;
    }


    /**
     * get grid representation of tile
     * @return arraylist representation of tile
     */
    public ArrayList<ArrayList<Segment>> getTile() { return this.tile; }

    /**
     * gives the name of the tile which is a letter between A and X
     * @return name of the tile as a string, i.e "A" or "B", ... "X"
     */
    public String getTileName() { return this.tileName; }

    /**
     * the number of clockwise rotations the tile has undergone
     * @return number of rotations modulo 4, because 4 rotations is the same as 0 rotations.
     */
    Integer getRotations() { return this.rotations % 4; }

    /**
     * set tile grid to be something else
     * @param tile replacement grid
     */
    public void setTile(ArrayList<ArrayList<Segment>> tile) { this.tile = tile; }


}
