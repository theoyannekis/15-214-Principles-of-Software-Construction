package edu.cmu.cs.cs214.hw4.core;

 class Follower {
    Player player;

    Follower(Player player) {
        this.player = player;
    }

    /**
     * gives the player who owns this follower
     * @return the player who played the follower
     */
    Player getPlayer() {
        return this.player;
    }
}
