package edu.cmu.cs.cs214.hw4.core;

public interface GameChangeListener {
    /**
     * Called when the tile the current player would like to play is rotated
     * @param carcassonne the carcassonne object which contains the newly rotated tile
     */
    void tileRotated(Carcassonne carcassonne);

    /**
     * Called whenever the turn changes and it becomes a different player's turn
     * @param carcassonne the carcassonne object which contains all information about the game
     */
    void changeTurn(Carcassonne carcassonne);

    /**
     * Called whenever the scores are updated or a follower is played
     * @param carcassonne the carcassonne object which contains all information about the game
     */
    void changeScoreOrFollowers(Carcassonne carcassonne);

    /**
     * Called when a new tile is drawn from the tile stack
     * @param carcassonne the carcassonne object which contains all information about the game     *
     */
    void changeTileCount(Carcassonne carcassonne);

    /**
     * Called when a tile is placed on the board
     * @param carcassonne the carcassonne object which contains all information about the game
     * @param row the row in the board of the changed tile
     * @param col the col in board of the changed tile
     */
    void changeBoard(Carcassonne carcassonne, int row, int col);

    /**
     * called when a follower is placed on a tile or moved to another feature on a tile
     * @param carcassonne the carcassonne object which contains all information about the game
     * @param boardRow the row in the board of the tile
     * @param boardCol the col in the board with the tile
     * @param follRow the row in the tile which the segment with the follower
     * @param follCol the col in the tile which the segment with the follower
     */
    void changeFollowerLocation(Carcassonne carcassonne, int boardRow, int boardCol, int follCol, int follRow);

    void endGame(Carcassonne carcassonne);


}
