package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.ArrayList;

 class CloisterFeature extends Feature {

    CloisterFeature(ArrayList<Segment> segmentList) {
        super(segmentList);
    }

    /**
     * Checks the completions status of a feature by exploring the tiles it exists on
     * @param tiles the tiles which the feature exists on
     * @param surroundedFlag true if the cloister is surrounded by played tiles
     * @return true if the cloister is surrounded by played tiles and false otherwise
     */
    boolean checkCompletionStatus(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles, boolean surroundedFlag) {
        if (tiles.size() != 1) {
            throw new IllegalStateException("Cloister features must only contain one segment");
        }
        return surroundedFlag;
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

        if (gameOverFlag && !this.isComplete() && this.hasFollower()) {

            score += numItems;
            this.removePlayersFollowersInFeature(player);

        }
        else {
            if (this.isComplete() && this.hasFollower()) {
                score += 9;
                this.removePlayersFollowersInFeature(player);
            }
        }
        return score;
    }
}