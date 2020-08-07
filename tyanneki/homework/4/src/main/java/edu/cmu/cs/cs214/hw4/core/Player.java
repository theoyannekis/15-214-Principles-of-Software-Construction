package edu.cmu.cs.cs214.hw4.core;

import java.util.ArrayList;

public class Player {
    private String name;
    private Integer score;
    private ArrayList<Follower> playedFollowers = new ArrayList<>();

    Player(Integer playerNumber) {
        this.name = "Player " + Integer.toString(playerNumber);
        this.score = 0;

    }

    /**
     * add a newly played follower to the player's list of followers
     * @param follower the follower to add
     */
    void addPlayedFollower(Follower follower) {
        this.playedFollowers.add(follower);
    }

    /**
     * return a follower to player by removing that follower from the playedFollowers list
     * @param follower the follower to remove
     */
    void returnFollower(Follower follower) {
        this.playedFollowers.remove(follower);
    }

    /**
     * give the number of followers that this player had in play currently
     * @return number of followers in play
     */
    int followersInPlay() {
        return this.playedFollowers.size();
    }

    public Integer followersLeft() { return 7 - this.playedFollowers.size(); }

    /**
     * add a value to the player's score
     * @param n the value by which to increase the score
     */
    void incrementScore(int n) {
        this.score += n;
    }

    /**
     * player's score
     * @return the player's score as an int
     */
    public Integer getScore() {
        return this.score;
    }

    /**
     * give the player's name
     * @return the player's name as a string
     */
    String getName() { return this.name; }

    /**
     * gets the player number
     * @return player number as an int
     */
    public int getPlayerNumber() {
        return Integer.parseInt(this.getName().substring(7));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
