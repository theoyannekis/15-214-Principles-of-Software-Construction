package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.*;

public abstract class Feature {
    private boolean completedFlag = false;
    ArrayList<Segment> segmentList;

    Feature(ArrayList<Segment> segmentList) {
        this.segmentList = segmentList;
    }

    /**
     *
     * @param numItems number of tiles for road and city, number of tiles in square for cloister, and number of
     *                 completed cities adjacent to a farm
     * @param gameOverFlag true if the game is over. false if not. Indicates which scoring system to use
     * @return score value of the feature given the scoring system and the inputs.
     */
    abstract int calculateScore(Player player, int numItems, boolean gameOverFlag);

    /**
     * Checks the completions status of a feature by exploring the tiles it exists on
     * @param tiles the tiles which the feature exists on
     * @param surroundedFlag true if the first segment in the feature is surrounded by played tiles
     * @return true if the feature is completed and false if it is not.
     */
    abstract boolean checkCompletionStatus(ArrayList<Pair<Tile, Pair<Integer, Integer>>> tiles, boolean surroundedFlag);

    /**
     * check completion flag
     * @return true if completion flag is true, false otherwise
     */
    boolean isComplete() {
        return this.completedFlag;
    }

    /**
     * gives the feature type which matches segment types. road to road, field to field, etc..
     * @return the feature type as a string
     */
    String featureType() {
        return this.segmentList.get(0).getType();
    }

    /**
     * Check if two features are of the same type. I.e. Farm and farm or city and city
     * @param feature the feature of which to compare to
     * @return boolean value representing if the two features match or not.
     */
    boolean sameFeatureType(Feature feature) {
        return this.featureType().equals(feature.featureType());
    }

    /**
     * check if the feature contains a given segment
     * @param segment segment to find in feature
     * @return true if segment is in feature's seglist, false otherwise
     */
    boolean contains(Segment segment) {
        return this.segmentList.contains(segment);
    }

    /**
     * checks if the feature has a follower on any of its segments
     * @return true if a segment in the feature has a follower and false if none do.
     */
    boolean hasFollower() {
        for (Segment segment : this.segmentList) {
            if (segment.hasFollower()) {
                return true;
            }
        }
        return false;
    }

    /**
     * combine the segments of two Features into one
     * @param comboFeature the feature which will be encompassed and removed
     */
    void combineFeatures(Feature comboFeature) {
        Set<Segment> segSet = new HashSet<>(this.segmentList);
        segSet.addAll(comboFeature.segmentList);
        this.segmentList = new ArrayList<>(segSet);
    }

    /**
     * set the completed flag on the feature to true indicating it is a completed feature
     */
    void setCompleted() {
        this.completedFlag = true;
    }

    /**
     * Give all segments that make up a feature
     * @return ArrayList of the segments that make up a feature
     */
    ArrayList<Segment> segmentsInFeature() {
        return this.segmentList;
    }

    /**
     * gets all of the followers on a certain feature along with corresponding segments
     * @return list of followers and segments they're on if there are any. null if there are no followers in feature.
     */
    private ArrayList<Pair<Follower, Segment>> followersAndSegInFeature() {
        ArrayList<Pair<Follower,Segment>> followersAndSeg = new ArrayList<>();
        for (Segment seg : this.segmentList){
            if (seg.hasFollower()) {
                followersAndSeg.add(new Pair<>(seg.getFollowerOnSegment(), seg));
            }
        }
        if (followersAndSeg.size() > 0) {
            return followersAndSeg;
        }
        return null;
    }

    /**
     * remove all of the followers belonging to a player from the feature
     * @param player the player who's followers need to be removed
     */
    void removePlayersFollowersInFeature(Player player) {
        ArrayList<Pair<Follower, Segment>> featureFollowersAndSeg = this.followersAndSegInFeature();
        if (featureFollowersAndSeg != null) {
            for (Pair<Follower, Segment> pair : featureFollowersAndSeg) {
                Follower follower = pair.getKey();
                Segment segment = pair.getValue();
                if (player.equals(follower.getPlayer())) {
                    player.returnFollower(follower);
                    segment.removeFollower();
                }
            }
        }
    }

    /**
     * gets a list of the player(s) with the most followers on a feature
     * @return a list of Players with the most followers on a feature. list will be of length 1 if one player had
     * the most and of length n if n players tied for the most followers
     */
    ArrayList<Player> mostFollowersOnFeature() {

        ArrayList<Player> winner = new ArrayList<>();
        ArrayList<Pair<Follower, Segment>> followersAndSeg = this.followersAndSegInFeature();
        //If there were no followers in feature return null
        if (followersAndSeg == null) {
            return null;
        }
        //Isolate the followers as we don't care about segments here
        ArrayList<Follower> followers = new ArrayList<>();
        for (Pair<Follower, Segment> pair : followersAndSeg) {
            followers.add(pair.getKey());
        }
        Map<Player, Integer> map = new HashMap<>();
        //Make a map of (player, numFollowers)
        for (Follower follower : followers) {
            Player player = follower.getPlayer();
            if (map.containsKey(follower.getPlayer())) {
                map.put(player, map.get(player) + 1);
            }
            else {
                map.put(player, 1);
            }
        }
        int maxFollowers = 0;
        for (Player player : map.keySet()) {
            int numFollowers = map.get(player);
            if (numFollowers > 0) {
                if (numFollowers > maxFollowers) {
                    winner = new ArrayList<>();
                    winner.add(player);
                    maxFollowers = numFollowers;
                }
                else if (numFollowers == maxFollowers) {
                    winner.add(player);
                }
            }
        }
        return winner;

    }

    /**
     * put a follower on the feature. Puts it on the first segment in the feature. If this segment is occupied then an
     * exception will be thrown because you are not allowed to play a follower on a feature that already has one
     * @param player the player the follower belongs to
     */
    void playFollowerOnFeature(Player player) {
        //Check if there is already a follower in feature
        for (Segment seg : this.segmentList) {
            if (seg.hasFollower()) {
                throw new IllegalStateException("Feature already has a follower");
            }
        }
        //Pick the first segment in feature to play the follower on
        Segment segment = this.segmentList.get(0);
        if (!segment.hasFollower()) {
            segment.playFollowerOnSegment(player);
        }

    }

    Pair<Integer, Integer> playFollowerOnFeatureInTile(Player player, Tile tile) {
        //Check if there is already a follower in feature
        for (Segment seg : this.segmentList) {
            if (seg.hasFollower()) {
                throw new IllegalStateException("Feature already has a follower");
            }
        }
        ArrayList<Segment> tileSegs = tile.getSegmentsInTile();
        //Loop through the feature and play follower on first segment we see in the tile
        for (Segment segment : this.segmentList) {
            if (!segment.hasFollower() && tileSegs.contains(segment)) {
                segment.playFollowerOnSegment(player);
                return tile.getSegPositionInTile(segment);
            }
        }
        return null;

    }


    @Override
    public String toString() {
        return this.segmentList.toString();
    }
}
