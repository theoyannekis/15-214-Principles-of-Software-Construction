package edu.cmu.cs.cs214.hw4.core;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Set;

/**
 * NOTE:
 * I had to comment out my gameplay tests because they depended on the tile stack being unshuffled. During
 * gameplay I need it to be shuffled so they fail in that case. I commented them out so the project would build
 */
public class Carcassonne {
    private TileStack tileStack;
    private Board board;
    private ArrayList<Player> players = new ArrayList<>();
    private boolean gameOverFlag = false;
    private Player activePlayer;
    private Tile currentTile;
    private final ArrayList<GameChangeListener> gameChangeListeners  = new ArrayList<>();
    private Feature justAccessedFeature = null;


    public Carcassonne(int numPlayers) {
        if (numPlayers < 2) {
            throw new IllegalArgumentException("Must choose at least two players");
        }
        if (numPlayers > 5) {
            throw new IllegalArgumentException("Maximum number of players is 5");
        }
        //Create the players
        for (int i = 1; i < numPlayers + 1; i++) {
            Player player = new Player(i);
            players.add(i - 1, player);
        }
        //Set the first active player to player1
        this.activePlayer = players.get(0);
        //Create the shuffled tileStack
        this.tileStack = new TileStack();
        //Now we need to shuffle the deck.
        tileStack.shuffleStack();
        //Not shuffling the deck for now because I can't test it properly when the tiles are random
        //Will add this in when the GUI is implemented
        //Create the game board containing the starting tile and dummy tiles
        this.board = new Board();

        this.currentTile = drawTile();

    }

    public void addGameChangeListener(GameChangeListener listener) {
        gameChangeListeners.add(listener);
    }

    private void notifyRotatedTile() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.tileRotated(this);
        }
    }

    private void notifyChangeTurn() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.changeTurn(this);
        }
    }

    private void notifyChangeScoreOrFollowers() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.changeScoreOrFollowers(this);
        }
    }

    private void notifyChangeTileCount() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.changeTileCount(this);
        }
    }

    private void notifyChangeBoard(int row, int col) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.changeBoard(this, row, col);
        }
    }

    private void notifyChangeFollowerLocation(int boardRow, int boardCol, int follRow, int follCol) {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.changeFollowerLocation(this, boardRow, boardCol, follRow, follCol);
        }
    }

    private  void notifyEndGame() {
        for (GameChangeListener listener : gameChangeListeners) {
            listener.endGame(this);
        }
    }

    private void setGameOverFlag() {
        this.gameOverFlag = true;
    }

    Player getPlayerByNumber(int playerNum) {
        if (playerNum < 1 || playerNum > this.players.size()) {
            throw new IllegalArgumentException("playerNum must be 1 through the number of players. No more no less");
        }
        return this.players.get(playerNum - 1);
    }

    /**
     * draw a tile off the top of the tileStack
     */
     public Tile drawTile() {
         if (this.tileStack.tilesLeft() == 0) {
             endGame();
             return null;
         }
          Tile tile = this.tileStack.pickTile();
          this.currentTile = tile;
          notifyChangeTileCount();
          notifyChangeTurn();
          return tile;
     }


    /**
     * changes the active player to the next player. Also updates the player scores
     */
    public void changePlayerTurn() {
        this.board.updateAllFeaturesCompletion();
        this.board.calculatePlayerScores(this.gameOverFlag);
        int numPlayers = this.players.size();
        int activePlayerNum = this.activePlayer.getPlayerNumber();
        if (activePlayerNum < numPlayers) {
            //Indexing starts at 0 so to move up one go to activePlayerNum in players array
            this.activePlayer = this.players.get(activePlayerNum);
        }
        else {
            this.activePlayer = this.players.get(0);
        }

        drawTile();
        notifyChangeScoreOrFollowers();
        notifyChangeTurn();

    }

    /**
     * rotate a tile clockwise n times
     * @param tile the tile to rotate
     * @param numRotations the number of clockwise rotations to perform
     */
    public void rotateTile(Tile tile, int numRotations) {
         for (int i = 0; i < numRotations; i++) {
             tile.rotateTile();
         }
         notifyRotatedTile();
    }

    private Set<Feature> getFeaturesOnTile(Tile tile) {
        return this.board.findFeaturesOnTile(tile);
    }

    /**
     * checks if a tile is a tile that has either been played or is the tile to be played
     * @param tile the tile to check
     * @return true if a tile is not a dummy tile. false if it is a dummy tile
     */
    public boolean isLiveTile(Tile tile) {
        return !tile.isDummyTile();
    }

    public String getTileName(Tile tile) {
        return tile.getTileName();
    }

    public void makeMove(Tile tile, int row, int col) {
        this.board.placeTile(row, col, tile);
        this.board.updateAllSurroundingFeatures(tile, row, col);
        notifyChangeBoard(row, col);
        notifyChangeScoreOrFollowers();
    }


    private void playFollower(Feature feature) {
        feature.playFollowerOnFeature(this.activePlayer);
    }

    public void playFollowerOnCurrentTile(int followerNum) {
        if (this.justAccessedFeature != null) {
            this.justAccessedFeature.removePlayersFollowersInFeature(this.activePlayer);
        }
        ArrayList<Feature> features = getFeaturesWithNoFollowersOnTile(this.currentTile);
        if (features.size() != 0) {
            //Protect from index out of bounds errors. we want followerNum to wrap around to 0
            int adjustedIndex = followerNum % (features.size());
            Feature featureAtIndex = features.get(adjustedIndex);
            this.justAccessedFeature = featureAtIndex;
            System.out.println(justAccessedFeature);
            Pair<Integer, Integer> tileLoc = this.board.locationOnBoard(this.currentTile);
            int boardRow = tileLoc.getKey();
            int boardCol = tileLoc.getValue();
            Pair<Integer, Integer> loc = featureAtIndex.playFollowerOnFeatureInTile(this.activePlayer, this.currentTile);
            int follRow = loc.getKey();
            int follCol = loc.getValue();

            notifyChangeFollowerLocation(boardRow, boardCol, follRow, follCol);
            notifyChangeScoreOrFollowers();
        }
    }

    private ArrayList<Feature> getFeaturesWithNoFollowersOnTile(Tile tile) {
        Set<Feature> allFeatures = getFeaturesOnTile(tile);
        ArrayList<Feature> feats = new ArrayList<>(allFeatures);
        ArrayList<Feature> noFollowers = new ArrayList<>();
        for (Feature feature : feats) {
            if (feature != null && !feature.hasFollower()) {
                noFollowers.add(feature);
            }
        }
        return noFollowers;
    }

    private void endGame() {
        this.setGameOverFlag();
        this.board.calculatePlayerScores(this.gameOverFlag);
        notifyEndGame();
    }

    public ArrayList<String> getWinner() {
        ArrayList<Player> players = this.getPlayers();
        ArrayList<String> winners = new ArrayList<>();
        int maxScore = 0;
        for (Player player : players) {
            if (player.getScore() > maxScore) {
                winners = new ArrayList<>();
                winners.add(player.getName());
            }
            else if (player.getScore() == maxScore) {
                winners.add(player.getName());
            }
        }

        return winners;
    }

    Board getBoard() {
        return this.board;
    }

    TileStack getTileStack() {
        return this.tileStack;
    }

    public Integer getTilesLeft() { return this.tileStack.tilesLeft(); }

    public Integer getTileRotations(Tile tile) { return tile.getRotations(); }

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    public Player getActivePlayer() {
        return this.activePlayer;
    }

    public Tile getTileAtLocation(int row, int col) { return this.board.getTileAtLocation(row, col); }

    public Tile getCurrentTile() { return this.currentTile; }

    public static void main(String[] args) {
        Carcassonne carcassonne = new Carcassonne(2);
        System.out.println(carcassonne.players);
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());

        Tile tile = carcassonne.drawTile();
        carcassonne.rotateTile(tile, 0);
        carcassonne.makeMove(tile, carcassonne.board.BOARDMIDINDEX, carcassonne.board.BOARDMIDINDEX - 1);
        //play follower on road below cloister
        carcassonne.playFollower(carcassonne.board.getFeatures().get(5));
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        Tile tile2 = carcassonne.drawTile();
        carcassonne.rotateTile(tile2, 2);
        carcassonne.makeMove(tile2, carcassonne.board.BOARDMIDINDEX + 1, carcassonne.board.BOARDMIDINDEX - 1);





        Tile tile3 = Tile.readInStartingTile();
        carcassonne.makeMove(tile3, carcassonne.board.BOARDMIDINDEX + 1, carcassonne.board.BOARDMIDINDEX - 1);
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        carcassonne.makeMove(tile2, carcassonne.board.BOARDMIDINDEX + 2, carcassonne.board.BOARDMIDINDEX - 1);
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        Tile tile4 = Tile.readInStartingTile();
        tile4.rotateTile();
        tile4.rotateTile();
        carcassonne.makeMove(tile4, carcassonne.board.BOARDMIDINDEX, carcassonne.board.BOARDMIDINDEX + 1);
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        carcassonne.board.getFeatures().get(8).playFollowerOnFeature(carcassonne.getPlayerByNumber(2));


        Tile tile5 = Tile.readInStartingTile();
        carcassonne.makeMove(tile5, carcassonne.board.BOARDMIDINDEX, carcassonne.board.BOARDMIDINDEX + 2);
        Board.printFeatures(carcassonne.board.getFeatures());
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        carcassonne.board.getFeatures().get(6).playFollowerOnFeature(carcassonne.getPlayerByNumber(2));


        Tile tile6 = Tile.readInStartingTile();
        tile6.rotateTile();
        tile6.rotateTile();
        System.out.println(carcassonne.players);
        carcassonne.makeMove(tile6, carcassonne.board.BOARDMIDINDEX, carcassonne.board.BOARDMIDINDEX + 3);
        System.out.println(carcassonne.players);
        Board.printFeatures(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);
        Board.printFeatureCompletion(carcassonne.board.getFeatures());
        System.out.println(carcassonne.players);

        carcassonne.endGame();
        System.out.println(carcassonne.players);
        






    }
}
