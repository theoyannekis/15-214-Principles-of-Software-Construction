package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.ArrayList;

 class RoadFeature extends Feature {

     RoadFeature(ArrayList<Segment> segmentList) {
        super(segmentList);
    }

    /**
     * Checks the completions status of a feature by exploring the tiles it exists on
     * @param tiles the tiles which the feature exists on
     * @param surroundedFlag true if the first segment in the feature is surrounded by played tiles
     * @return true if there are two road ends in the feature and false otherwise
     */
    boolean checkCompletionStatus(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles, boolean surroundedFlag) {
        int count = 0;
        for (Segment seg : this.segmentList) {
            if (seg.isRoadEnd()) {
                count += 1;
            }
        }
        if (count > 2) {
            throw new IllegalStateException("More than 2 road ends in feature");
        }
        return (count == 2);

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
        //If its complete and has a follower it has not been scored. So score and return follower
        else if (this.isComplete() && this.hasFollower()) {

            score += numItems;
            this.removePlayersFollowersInFeature(player);
        }
        return score;
    }

}
