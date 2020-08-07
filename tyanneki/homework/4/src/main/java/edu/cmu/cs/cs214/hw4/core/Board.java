package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.*;

public class Board {
    //BOARDSIZE is the dimension of the board. Make it an odd number so there is a middle tile.
    private static final int BOARDSIZE = 71;
    final int BOARDMIDINDEX = BOARDSIZE / 2;
    private ArrayList<ArrayList<Tile>> board = new ArrayList<>();
    private ArrayList<Feature> features;

    Board() {
        //Initialize the board to be a BOARDSIZE x BOARDSIZE grid filled with dummyTiles
        for (int i = 0; i < BOARDSIZE; i++) {
            ArrayList<Tile> row = new ArrayList<>();
            for (int j = 0; j < BOARDSIZE; j++) {
                //Dummy tiles indicate unfilled spots
                Tile dummyTile = Tile.createDummyTile();
                row.add(j, dummyTile);
            }
            this.board.add(row);
        }
        //Put the startingTile in the middle of the grid. This tile is the same every game.
        Tile startingTile = Tile.readInStartingTile();
        this.board.get(BOARDMIDINDEX).set(BOARDMIDINDEX, startingTile);
        this.features = startingTile.constructTileFeatures();

    }

    /**
     * checks if a placement would result in a tile being surrounded on all sides by dummy tiles
     * @param row the row on the board
     * @param col the column on the board
     * @return true if all adjacent tiles are dummy. False if any are not
     */
    private boolean surroundedByDummyTiles(int row, int col) {
        if (!this.board.get(row).get(col + 1).isDummyTile()) { return false; }
        else if (!this.board.get(row).get(col - 1).isDummyTile()) { return false; }
        else if (!this.board.get(row + 1).get(col).isDummyTile()) { return false; }
        else if (!this.board.get(row - 1).get(col).isDummyTile()) { return false; }
        else { return true; }
    }

    /**
     * Checks if all surrounding tiles are played tiles. I.e not dummy tiles
     * @param row row on the board
     * @param col column on the board
     * @return true if none of the surrounding tiles are dummy tiles
     */
    private boolean surroundedByPlayedTiles(int row, int col) {
        if (this.board.get(row).get(col + 1).isDummyTile()) { return false; }
        else if (this.board.get(row).get(col - 1).isDummyTile()) { return false; }
        else if (this.board.get(row + 1).get(col).isDummyTile()) { return false; }
        else if (this.board.get(row - 1).get(col).isDummyTile()) { return false; }
        else { return true; }
    }

    /**
     * place a tile on the board in given position. Throws exception if invalid placement. Also Updates features to
     * include the new tile's
     * @param row row on board to place tile
     * @param col col on board to place tile
     * @param tile the tile to place on the board
     */
    void placeTile(int row, int col, Tile tile) {
        //If board at specified location is occupied (i.e not dummy tile), throw illegalArgumentException
        if (!this.board.get(row).get(col).isDummyTile()) {
            throw new IllegalArgumentException("Board location already occupied");
        }
        //If the segments of the placed tile don't match those of surrounding tiles
        else if (!validSegmentMatches(row, col, tile)) {
            throw new IllegalArgumentException("Segments of surrounding tiles must match placed tile");
        }
        //If the user attempts to place the tile in a spot not touching any played tiles
        else if (surroundedByDummyTiles(row, col)) {
            throw new IllegalArgumentException("Must play Tile adjacent to another played tile");
        }
        else {
            this.board.get(row).set(col, tile);
            this.features.addAll(tile.constructTileFeatures());
        }
    }

    /**
     * find the feature where the given segment exists
     * @param segment segment to find the feature of
     * @return feature where the segment lives
     */
     private Feature findFeatureContainingSegment(Segment segment) {
        for (Feature feat : this.features) {
            if (feat.contains(segment)) {
                return feat;
            }
        }
        return null;
    }

    /**
     * gets a set of all features that live on a tile.
     * @param tile the tile to find the features it is a part of
     * @return a set of the features that live on a tile
     */
    Set<Feature> findFeaturesOnTile(Tile tile) {
         ArrayList<Segment> segs = tile.getSegmentsInTile();
         Set<Feature> features = new HashSet<>();
         for (Segment seg : segs) {
             features.add(findFeatureContainingSegment(seg));
         }
         return features;

    }

    /**
     * finds the tile in the board in which the given segment lives
     * @param segment the segment we wish to find out on which tile it is
     * @return the tile the segment is on or null if the segment is not on any tile in the board
     */
    private Tile findTileContainingSegment(Segment segment) {
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Tile tile = getTileAtLocation(i, j);
                if (!tile.isDummyTile() && tile.containsSegment(segment)) {
                    return tile;
                }
            }
        }
        return null;
    }


    /**
     * looks through all of the features on the board and finds any completed cities
     * @return list of completed cities
     */
    private ArrayList<Feature> findCompletedCities() {
        //first find all completed cities
        ArrayList<Feature> completedCities = new ArrayList<>();
        for (Feature feature : this.features) {
            if (feature.featureType().equals("city") && feature.isComplete()) {
                completedCities.add(feature);
            }
        }
        if (completedCities.size() > 0) {
            return completedCities;
        }
        return null;
    }


    /**
     * finds all farms adjacent to the given feature
     * @param feat the feature we'd like to find the adjacent farms to
     * @return set of the farms adjacent to feature
     */
    private Set<Feature> findAdjacentFarms(Feature feat) {
        Set<Feature> adjFarms = new HashSet<>();
        ArrayList<Pair<Tile, Pair<Integer, Integer>>> tilePairs = findTilesOfFeature(feat);
        for (Pair<Tile, Pair<Integer, Integer>> pair : tilePairs) {
            Tile tile = pair.getKey();
            //get all field segments adjacent to the feature
            Set<Segment> adjSet = tile.getAdjacentFieldSegmentsToFeature(feat);
            //Using those, find all the farms adjacent to the feature
            for (Segment seg : adjSet) {
                Feature feature = findFeatureContainingSegment(seg);
                adjFarms.add(feature);
            }
        }
        if (adjFarms.size() > 0) {
            return adjFarms;
        }
        return null;
    }

    /**
     * finds all farms which supply completed cities
     * @return map of each farm that supplies a completed city to how many completed cities it supplies
     */
    Map<Feature, Integer> farmsSupplyingCities() {
        ArrayList<Feature> compCities = findCompletedCities();
        Map<Feature, Integer> farmToCitiesMap = new HashMap<>();
        //If there are no completed cities
        if (compCities == null) {
            return null;
        }
        for (Feature city : compCities) {
            Set<Feature> adjFarm = findAdjacentFarms(city);
            if (adjFarm == null) {
                return null;
            }
            for (Feature feat : adjFarm) {
                if (farmToCitiesMap.containsKey(feat)) {
                    farmToCitiesMap.put(feat, farmToCitiesMap.get(feat) + 1);
                }
                else {
                    farmToCitiesMap.put(feat, 1);
                }
            }
        }
        return farmToCitiesMap;
    }

    /**
     * how many completed cities does a particular farm supply
     * @param farm the farm
     * @return integer value of how many completed cities are adjacent to a farm
     */
    private int numCitiesSupplied(Feature farm) {
        Map<Feature, Integer> map = farmsSupplyingCities();
        if (map == null) {
            return 0;
        }
        for (Feature feature : map.keySet()) {
            if (feature.equals(farm)) {
                return map.get(feature);
            }
        }
        return 0;
    }


    /**
     * updates all features next to a newly placed tile to include/merge with the new features on the tile
     * @param placedTile newly placed tile
     * @param row row of the placed tile
     * @param col column of the placed tile
     */
    void updateAllSurroundingFeatures(Tile placedTile, int row, int col) {
        Tile northTile = getTileAtLocation(row - 1, col);
        if (!northTile.isDummyTile()) {
                updateFeatures(placedTile, northTile, "north", "south");
        }
        Tile southTile = getTileAtLocation(row + 1, col);
        if (!southTile.isDummyTile()) {
            updateFeatures(placedTile, southTile, "south", "north");
        }
        Tile westTile = getTileAtLocation(row, col - 1);
        if (!westTile.isDummyTile()) {
            updateFeatures(placedTile, westTile, "west", "east");
        }
        Tile eastTile = getTileAtLocation(row, col + 1);
        if (!eastTile.isDummyTile()) {
            updateFeatures(placedTile, eastTile, "east", "west");
        }
    }

    /**
     * update the features of two adjacent tiles by merging them if necessary
     * @param placedTile newly placed tile
     * @param adjacentTile tile adjacent to placed tile
     * @param placedEdge The edge of the placed tile touching the adjacent tile "north", "west", etc...
     * @param adjacentEdge the edge of the adjacent tile touching the placed tile
     */
    private void updateFeatures(Tile placedTile, Tile adjacentTile, String placedEdge, String adjacentEdge) {
        ArrayList<Segment> placeTileSegs = placedTile.getSegmentsOnSide(placedEdge);
        ArrayList<Segment> adjacentTileSegs = adjacentTile.getSegmentsOnSide(adjacentEdge);
        Set<Feature> toRemove = new HashSet<>();
        for (int i = 0; i < placeTileSegs.size(); i++) {
            Segment placedTileSeg = placeTileSegs.get(i);
            Segment adjacentSeg = adjacentTileSegs.get(i);
            Feature adjacentFeature = findFeatureContainingSegment(adjacentSeg);
            Feature placedFeature = findFeatureContainingSegment(placedTileSeg);
            if (adjacentFeature == null || placedFeature == null) {
                throw new IllegalStateException("Could not find specified feature on placed tile");
            }
            //If adjacent feature is not completed already and they share feature type
            if (!adjacentFeature.isComplete() && adjacentFeature.sameFeatureType(placedFeature)
                                                      && !adjacentFeature.equals(placedFeature)) {
                //Add segments of the placedFeature to the adjacentFeature
                adjacentFeature.combineFeatures(placedFeature);
                toRemove.add(placedFeature);
            }

        }
        //Now remove all redundant features. i.e. those features that have been encompassed into others.
        for (Feature feat : toRemove) {
            this.features.remove(feat);
        }

    }

    /**
     * checks to see if a tile in the given position matches the segments of the tiles around it.
     * @param row row of tile in board
     * @param col col of tile in board
     * @param tile the tile itself
     * @return true if tile matches segments of those around it
     */
    private boolean validSegmentMatches(int row, int col, Tile tile) {
        //Check for out of bounds errors. The board is large enough that this should never happen
        if (row <= 0 || row >= BOARDSIZE - 1 || col <= 0 || col >= BOARDSIZE - 1 ) { return false; }
        Tile northTile = getTileAtLocation(row - 1, col);
        Tile westTile = getTileAtLocation(row, col - 1);
        Tile southTile = getTileAtLocation(row + 1, col);
        Tile eastTile = getTileAtLocation(row, col + 1);
        return tile.sidesMatch("north", northTile)
                && tile.sidesMatch("west", westTile)
                && tile.sidesMatch("south", southTile)
                && tile.sidesMatch("east", eastTile);
    }


    /**
     * Finds a list of tiles in which the feature lives on
     * @param feature the feature of which we want to find which tiles they are on
     * @return an ArrayList containing all the tiles in which the feature lives on with their location in board
     */
    ArrayList<Pair<Tile, Pair<Integer, Integer>>> findTilesOfFeature(Feature feature) {
        Set<Pair<Tile, Pair<Integer, Integer>>> tileSet = new HashSet<>();
        ArrayList<Segment> segList = feature.segmentsInFeature();
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                Tile tile = getTileAtLocation(i, j);
                if (!tile.isDummyTile()) {
                    for (Segment seg : segList) {
                        if (tile.containsSegment(seg)) {
                            tileSet.add(new Pair<>(tile, new Pair<>(i, j)));
                        }
                    }
                }
            }
        }
        return new ArrayList<>(tileSet);
    }

    /**
     * update all the features in the board by checking if they are complete or not
     */
    void updateAllFeaturesCompletion() {
        for (Feature feat : this.features) {
            updateFeatureCompletion(feat);
        }
    }

    /**
     * update an individual feature to see if its complete or not. This is handled by the feature's class
     * @param feature feature on the board
     */
    private void updateFeatureCompletion(Feature feature) {
        String featureType = feature.featureType();
        // Get all of the tiles that this feature exists on
        ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles = findTilesOfFeature(feature);
        //Feature surrounded by played tiles
        int row = tiles.get(0).getValue().getKey();
        int col = tiles.get(0).getValue().getValue();
        boolean surroundedFlag = surroundedByPlayedTiles(row, col);

        if (feature.checkCompletionStatus(tiles, surroundedFlag)) {
            feature.setCompleted();
        }

    }

    /**
     * recounts every player in the game's score. different scoring methods if game is over or not
     * @param gameOverFlag true if game is over, false otherwise
     */
    void calculatePlayerScores(boolean gameOverFlag) {
        for (Feature feature : this.features) {
            ArrayList<Player> mostFollowersPlayers = feature.mostFollowersOnFeature();
            //If there are followers on the feature
            if (mostFollowersPlayers != null) {
                for (Player player : mostFollowersPlayers) {
                    int numItems;
                    switch (feature.featureType()) {
                        case "cloister":
                            Tile cloisterTile = this.findTilesOfFeature(feature).get(0).getKey();
                            int surroundingTiles = numSurroundingPlayedTiles(cloisterTile);
                            //Add one to include the cloister tile
                            numItems = surroundingTiles + 1;
                            break;
                        case "field":
                            numItems = numCitiesSupplied(feature);
                            break;
                        default:
                            //find how many tiles are involved with this feature
                            numItems = this.findTilesOfFeature(feature).size();

                            break;
                    }
                    int scoreIncrement = feature.calculateScore(player, numItems, gameOverFlag);
                    player.incrementScore(scoreIncrement);
                }
            }

        }
    }

    /**
     * gets the row and col of a tile on the board
     * @param tile tile to find position of
     * @return row and col given as a Pair<>
     */
    Pair<Integer, Integer> locationOnBoard(Tile tile) {
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (tile.equals(this.board.get(i).get(j))) {
                    return new Pair<>(i, j);
                }
            }
        }
        return null;
    }

    /**
     * find the number of surrounding tiles that are played, not dummy tiles. This includes diagonal adjacency
     * @param tile tile to branch out from
     * @return integer number of tiles surrounding given tile that are played
     */
    int numSurroundingPlayedTiles(Tile tile) {
        Pair<Integer, Integer> pair = locationOnBoard(tile);
        if (pair == null) {
            return 0;
        }
        int row = pair.getKey();
        int col = pair.getValue();
        int playedCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i != 0 || j != 0) {
                    Tile adjTile = this.board.get(row + i).get(col + j);
                    if (!adjTile.isDummyTile()) {
                        playedCount += 1;
                    }
                }
            }
        }
        return playedCount;

    }

    /**
     * tile at given row and col
     * @param row row on board
     * @param col col on board
     * @return the tile at that position
     */
    Tile getTileAtLocation(int row, int col) {
        return this.board.get(row).get(col);
    }

    ArrayList<Feature> getFeatures() { return this.features; }

    /**
     * print all features on the board
     * @param features list of features on board
     */
    static void printFeatures(ArrayList<Feature> features) {
        for (Feature feat : features) {
            System.out.println(feat);
        }
        System.out.println();
    }

    /**
     * print all the feature's completicn status
     * @param features list of all features on board
     */
    static void printFeatureCompletion(ArrayList<Feature> features) {
        for (Feature feat : features) {
            System.out.println(feat.isComplete());
        }
        System.out.println();
    }

    ArrayList<ArrayList<Tile>> getBoard() {
        return this.board;
    }

}
