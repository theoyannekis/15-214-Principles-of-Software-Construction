package edu.cmu.cs.cs214.hw4.core;


import javafx.util.Pair;

import java.util.ArrayList;

 class CityFeature extends Feature {

    CityFeature(ArrayList<Segment> segmentList) {
        super(segmentList);
    }

    private int countPennants() {
        int pennants = 0;
        for (Segment segment : this.segmentList) {
            if (segment.hasPennant()) {
                pennants += 1;
            }
        }
        return pennants;
    }

    /**
     * find the tile among the given list at the given row and col
     * @param tilePairs the list of tiles and locations to look through
     * @param row the row of the tile we want
     * @param col the column of the tile we want
     * @return the tile at the given position or null if its not there
     */
    private Tile findTile(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tilePairs, int row, int col) {
        for (Pair<Tile, Pair<Integer, Integer>> pair : tilePairs) {
            int r = pair.getValue().getKey();
            int c = pair.getValue().getValue();
            if (r == row && c == col) {
                return pair.getKey();
            }
        }
        return null;
    }

    /**
     * Checks the completions status of a feature by exploring the tiles it exists on. Checks all tile edges with cities
     * on the edge to see if the adjacent edge also has a city.
     * @param tiles the tiles which the feature exists on
     * @param surroundedFlag true if the first segment in the feature is surrounded by played tiles
     * @return true if there are no mistmatched city segments, i.e the city is completed.
     */
    boolean checkCompletionStatus(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles, boolean surroundedFlag) {
        boolean isMismatch = false;

        for (Pair<Tile, Pair<Integer, Integer>> pair : tiles) {
            Tile tile = pair.getKey();
            int row = pair.getValue().getKey();
            int col = pair.getValue().getValue();
            ArrayList<String> citySides = tile.citySides();

            if (citySides.contains("north")) {
                Tile foundTile = findTile(tiles, row - 1, col);
                if (foundTile != null) {
                    ArrayList<String> northSides = foundTile.citySides();
                    if (!northSides.contains("south")) {
                        isMismatch = true;
                    }
                }
                else {
                    isMismatch = true;
                }
            }
            if (citySides.contains("west")) {
                Tile foundTile = findTile(tiles, row, col - 1);
                if (foundTile != null) {
                    ArrayList<String> northSides = foundTile.citySides();
                    if (!northSides.contains("east")) {
                        isMismatch = true;
                    }
                }
                else {
                    isMismatch = true;
                }
            }
            if (citySides.contains("south")) {
                Tile foundTile = findTile(tiles, row + 1, col);
                if (foundTile != null) {
                    ArrayList<String> northSides = foundTile.citySides();
                    if (!northSides.contains("north")) {
                        isMismatch = true;
                    }
                }
                else {
                    isMismatch = true;
                }
            }
            if (citySides.contains("east")) {
                Tile foundTile = findTile(tiles, row, col + 1);
                if (foundTile != null) {
                    ArrayList<String> northSides = foundTile.citySides();
                    if (!northSides.contains("west")) {
                        isMismatch = true;
                    }
                }
                else {
                    isMismatch = true;
                }
            }
        }
        return !isMismatch;
    }

    /**
     *
     * @param numItems number of tiles for road and city, number of tiles in square for cloister, and number of
     *                 completed cities adjacent to a farm
     * @param gameOverFlag true if the game is over. false if not. Indicates which scoring system to use
     * @return score value of the feature given the scoring system and the inputs.
     */
    int calculateScore(Player player, int numItems, boolean gameOverFlag) {
        int score = 0;
        int pennants = this.countPennants();
        if (gameOverFlag && !this.isComplete() && this.hasFollower()) {
            score += (pennants + numItems);
            this.removePlayersFollowersInFeature(player);

        }
        else {
            if (this.isComplete() && this.hasFollower()) {
                score += (2 * pennants + 2 * numItems);
                this.removePlayersFollowersInFeature(player);
            }
        }
        return score;
    }
}
