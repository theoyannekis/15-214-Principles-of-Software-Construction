package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.ArrayList;

 class FieldFeature extends Feature {

    FieldFeature(ArrayList<Segment> segmentList) {
        super(segmentList);
    }

    /**
     * Checks the completions status of a feature by exploring the tiles it exists on
     * @param tiles the tiles which the feature exists on
     * @param surroundedFlag true if the first segment in the feature is surrounded by played tiles
     * @return false always because a farm is never completed
     */
    boolean checkCompletionStatus(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles, boolean surroundedFlag) {
        return false;
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
        //Only score at the end of the game
        if (gameOverFlag && this.hasFollower()) {

            score += numItems * 3;
            this.removePlayersFollowersInFeature(player);

        }
        return score;
    }
}