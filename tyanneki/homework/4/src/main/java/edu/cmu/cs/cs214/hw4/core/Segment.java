package edu.cmu.cs.cs214.hw4.core;


public class Segment {
    //Segment could be dummy!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    private String segString;
    private Follower follower = null;
    private boolean pennant = false;
    private boolean roadEnd = false;

    Segment(String segString) {
        this.segString = segString;
        //segString.contains("city")
        if (segString.equals("cityPennant")) {
            this.segString = "city";
            this.pennant = true;
        }
        else if (segString.equals("roadEnd")) {
            this.segString = "road";
            this.roadEnd = true;
        }
    }

    /**
     * finds out if the segment is a dummy segment indicated by a segstring of "dummy"
     * @return true if it is a dummy segment and false otherwise
     */
    boolean isDummySegment() {
        return this.sameSegmentType(new Segment("dummy"));
    }

    /**
     * segments match if at least one is a dummy segment or they are of the same segment type
     * @param firstSeg first segment to compare
     * @param secondSeg second segment to compare the first to
     * @return true if segments match and false otherwise
     */
    static boolean segmentsMatch(Segment firstSeg, Segment secondSeg) {
       return firstSeg.isDummySegment()
               || secondSeg.isDummySegment()
               || firstSeg.sameSegmentType(secondSeg);
    }

    /**
     * checks if of same segment type. "road" and "roadEnd" are of same type
     * @param segment segment to compare the segment object to
     * @return true if they match types and false otherwise
     */
    boolean sameSegmentType(Segment segment) {
        return segment.segString.equals(this.segString);
    }

    /**
     * Get the follower on the segment
     * @return null if there is no follower on segment. Returns the Follower if there is one there
     */
    Follower getFollowerOnSegment() {
        return this.follower;
    }

    /**
     * check if the segment has a follower by checking the follower field
     * @return true if segment has a follower
     */
    boolean hasFollower() {
        return this.follower != null;
    }

    /**
     * Places a follower on the segment. Throws an exception and does not place it if the player has already played
     * all 7 of their followers.
     * @param player the player who's follower is being placed on the board
     */
    void playFollowerOnSegment(Player player) {
        if (player.followersInPlay() >= 7) {
            throw new IllegalStateException("Player already has 7 followers in play");
        }
        if (this.hasFollower()) {
            throw new IllegalStateException("Segment already has a follower");
        }
        Follower follower = new Follower(player);
        this.follower = follower;
        player.addPlayedFollower(follower);
    }

    /**
     * get the type of the segment so "road", "field", "city", or "cloister"
     * @return the type as a string
     */
    String getType() {
        return this.segString;
    }

    /**
     * remove a follower from segment
     */
    void removeFollower() {
        this.follower = null;
    }

    /**
     * checks if a segment is a road end. used to set segstring to "road" because those match roadEnds
     * @return true if roadEnd false if not.
     */
    boolean isRoadEnd() {
        return this.roadEnd;
    }

    /**
     * checks if a segment has a pennant. only cities can have pennants
     * @return true if segment has pennant false if not
     */
    boolean hasPennant() { return this.pennant; }


    @Override
    public String toString() {
        String followerString = "";
        if (this.follower != null) {
            followerString = " with follower";
        }
        return segString + followerString;
    }



}
