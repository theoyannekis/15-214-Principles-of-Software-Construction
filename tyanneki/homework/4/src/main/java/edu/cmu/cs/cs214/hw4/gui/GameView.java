package edu.cmu.cs.cs214.hw4.gui;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.GameChangeListener;
import edu.cmu.cs.cs214.hw4.core.Player;
import edu.cmu.cs.cs214.hw4.core.Tile;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;


class GameView extends JPanel implements GameChangeListener {

    private static final float FONT_SIZE = 20.0f;
    private static final int TILE_WIDTH = 90;
    private static final int TILE_HEIGHT = 90;
    private static final int IMAGE_COLS = 6;
    private static final int BOARD_ROWS = 71;
    private static final int BOARD_COLS = 71;
    private static final int CIRCLE_RADIUS = 10;



    private Carcassonne carcassonne;
    private JPanel sidePanel;
    private JPanel currentTileDisplay;
    private JLabel activePlayerLabel;
    private JLabel tilesLeftLabel;
    private ArrayList<JLabel> playerLabels = new ArrayList<>();
    private JScrollPane boardScroll;
    private JPanel boardPanel;
    private ArrayList<ArrayList<JButton>> boardTiles = new ArrayList<>();
    private BufferedImage image;
    private boolean tilePlayedOnTurn = false;
    private Integer followerNum = 0;
    ArrayList<Color> colors = new ArrayList<>();


    GameView(Carcassonne carcassonne) {
        if (carcassonne == null) {
            throw new NullPointerException("carcassonne argument must not be null.");
        }

        try {
            this.image = ImageIO.read(new File("src/main/resources/Carcassonne.png"));

            this.carcassonne = carcassonne;

            this.colors.add(Color.BLUE);
            this.colors.add(Color.RED);
            this.colors.add(Color.MAGENTA);
            this.colors.add(Color.GREEN);
            this.colors.add(Color.GRAY);

            setLayout(new BorderLayout());
            // Set up the panel on the right side of the screen with all of the game information besides for the board
            this.sidePanel = new JPanel();
            this.sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
            this.sidePanel.setPreferredSize(new Dimension(300, 1010));

            createSidePanel();
            this.sidePanel.setBorder(BorderFactory.createLineBorder(Color.black));
            add(this.sidePanel, BorderLayout.EAST);

            this.boardPanel = new JPanel();
            this.boardPanel.setLayout(new GridLayout(BOARD_ROWS, BOARD_COLS));

            this.boardScroll = new JScrollPane(this.boardPanel);
            this.boardScroll.setPreferredSize(new Dimension(1010, 1010));
            this.boardScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
            this.boardScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            createBoardPanel();
            add(this.boardScroll, BorderLayout.WEST);

            setVisible(true);
        } catch (Exception e) {
            String message = "COULD NOT FIND FILE FOR TILE IMAGE";
            JOptionPane.showMessageDialog(this.getRootPane(), message, "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    private void createSidePanel() {

        JPanel playerScoreDisplay = createPlayerScoreDisplay();
        JPanel tilesLeftDisplay = createTilesLeftDisplay();
        JPanel activePlayerDisplay = createActivePlayerDisplay();
        this.currentTileDisplay = createCurrentTileDisplay();
        JButton instructionButton = new JButton();
        instructionButton.setText("Instructions");
        String instMessage = "1. To find the starting tile you need to scroll to the middle of the board.\n" +
                "2. Rotate the tile with the button as many times as you like before playing it.\n" +
                "3. Play a tile by clicking a slot on the board.\n" +
                "4. Only discard a tile if there is nowhere to play it.\n" +
                "5. To play a follower: play a tile first, then click play follower.\n" +
                "It will place a dot indicating which feature your follower was placed on.\n" +
                "6. To move the follower to a new feature on the tile keep clicking it.\n" +
                "Where in the feature is placed is a bit confusing so keep cycling through the\n" +
                "possibilities until you're sure you're playing it where you want to.\n" +
                "7. I didn't have time to implement removing the circles when a follower is removed so they persist\n" +
                "but they will be removed in the back-end.\n" +
                "8. Scores and the tile image are updated when the change turn button is pressed";

        instructionButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(instructionButton, instMessage, "Instructions",
                    JOptionPane.INFORMATION_MESSAGE);

        });
        JButton turnButton = new JButton();
        turnButton.setText("Change Turn");
        turnButton.addActionListener(e -> {
            if (tilePlayedOnTurn) {
                this.carcassonne.changePlayerTurn();
            }
            else {
                String message = "Must play a tile before changing turn";
                JOptionPane.showMessageDialog(turnButton, message, "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton followerButton = new JButton();
        followerButton.setText("Play Follower");
        followerButton.addActionListener(e -> {
            if (tilePlayedOnTurn) {
                this.carcassonne.playFollowerOnCurrentTile(this.followerNum);
                this.followerNum += 1;
            }
            else {
                String message = "Must play a tile before playing a follower";
                JOptionPane.showMessageDialog(followerButton, message, "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton discardButton = new JButton();
        discardButton.setText("Discard Tile");
        discardButton.addActionListener(e -> {
            this.carcassonne.drawTile();
        });

        this.sidePanel.add(playerScoreDisplay);
        this.sidePanel.add(tilesLeftDisplay);
        this.sidePanel.add(instructionButton);
        this.sidePanel.add(activePlayerDisplay);
        this.sidePanel.add(turnButton);
        this.sidePanel.add(followerButton);
        this.sidePanel.add(discardButton);
        this.sidePanel.add(this.currentTileDisplay);


        this.sidePanel.setVisible(true);

    }

    private JPanel createPlayerScoreDisplay() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new BoxLayout(playerPanel, BoxLayout.Y_AXIS));
        ArrayList<Player> players = this.carcassonne.getPlayers();

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String playerStr = "<html>Player: " + player.getPlayerNumber() + "       Score: " +
                    player.getScore().toString() + "<BR>" + "Followers Left: " +
                    player.followersLeft().toString() + "<html>";
            final JLabel label = new JLabel(playerStr);
            label.setForeground(this.colors.get(i));
            label.setFont(label.getFont().deriveFont(FONT_SIZE));
            playerPanel.add(label);
            this.playerLabels.add(label);
        }

        playerPanel.setVisible(true);
        return playerPanel;
    }

    private JPanel createActivePlayerDisplay() {
        JPanel activePanel = new JPanel();
        activePanel.setLayout(new BoxLayout(activePanel, BoxLayout.Y_AXIS));
        String playerStr = this.carcassonne.getActivePlayer() + "'s Turn";
        final JLabel label = new JLabel(playerStr);
        label.setFont(label.getFont().deriveFont(FONT_SIZE));
        this.activePlayerLabel = label;
        activePanel.add(this.activePlayerLabel);

        activePanel.setVisible(true);
        return activePanel;
    }

    private JPanel createCurrentTileDisplay() {
        JPanel currentTilePanel = new JPanel();

        String tileStr = "Current Tile: ";
        final JLabel label = new JLabel(tileStr);
        label.setFont(label.getFont().deriveFont(FONT_SIZE));
        Tile currentTile = this.carcassonne.getCurrentTile();
        JButton currentTileButton = createTileButton(currentTile);
        JButton rotateButton = new JButton();
        rotateButton.setText("Rotate Tile");
        rotateButton.addActionListener(e -> {
            if (!this.tilePlayedOnTurn) {
                this.carcassonne.rotateTile(currentTile, 1);
            }
        });

        currentTilePanel.add(label);
        currentTilePanel.add(currentTileButton);
        currentTilePanel.add(rotateButton);

        currentTilePanel.setVisible(true);
        return currentTilePanel;

    }

    private JPanel createTilesLeftDisplay() {
        JPanel tilesLeftPanel = new JPanel();

        Integer tilesLeft = this.carcassonne.getTilesLeft();

        String tilesStr = "Tiles Remaining: " + tilesLeft.toString();
        this.tilesLeftLabel = new JLabel(tilesStr);
        this.tilesLeftLabel.setFont(this.tilesLeftLabel.getFont().deriveFont(FONT_SIZE));
        tilesLeftPanel.add(this.tilesLeftLabel);

        tilesLeftPanel.setVisible(true);
        return tilesLeftPanel;
    }

    private JButton createTileButton(Tile tile) {
        JButton tileButton = new JButton();

        Dimension panelD = new Dimension(TILE_WIDTH, TILE_HEIGHT);
        tileButton.setPreferredSize(panelD);

        if (this.carcassonne.isLiveTile(tile)) {
            addImageToButton(tile, tileButton, -1, -1);
        }
        tileButton.setVisible(true);
        return tileButton;
    }

    private void addImageToButton(Tile tile, JButton tileButton, int follRow, int follCol) {
        Pair<Integer, Integer> xAndY = getTopAndLeftFromTileName(this.carcassonne.getTileName(tile));
        int x = xAndY.getKey();
        int y = xAndY.getValue();
        BufferedImage tileImage = this.image.getSubimage(x, y, TILE_WIDTH, TILE_HEIGHT);
        //Now rotate the image
        Integer rotations = this.carcassonne.getTileRotations(tile);
        tileImage = rotateClockwise(tileImage, rotations);
        //Now add a follower if specified
        if (follRow != -1 && follCol != -1) {
            Pair<Integer, Integer> follPos = getXAndYFromFollowerPos(follRow, follCol);
            int follX = follPos.getKey();
            int follY = follPos.getValue();
            Color color = this.colors.get(this.carcassonne.getActivePlayer().getPlayerNumber() - 1);
            tileImage = withCircle(tileImage, color, follX, follY, CIRCLE_RADIUS);

        }
        ImageIcon icon = new ImageIcon(tileImage);
        tileButton.setIcon(icon);
    }

    private Pair<Integer, Integer> getXAndYFromFollowerPos(int row, int col) {
        return new Pair<>(col * TILE_WIDTH / 5, row * TILE_HEIGHT / 5);
    }

    private static BufferedImage withCircle(BufferedImage src, Color color, int x, int y, int radius) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), src.getType());

        Graphics2D g = (Graphics2D) dest.getGraphics();
        g.drawImage(src, 0, 0, null);
        g.setColor(color);
        g.fillOval(x - radius, y - radius, 2*radius, 2*radius);
        g.dispose();

        return dest;
    }

    /**
     * gives the x-coordinate of the left side of the tile and the y-coordinate of the top of the tile in
     * the image file 'Carcassonne.png'
     * @param tileName the name of the tile which will be a string from 'A' to 'X'
     * @return a pair of Pair<X,Y>
     */
    private static Pair<Integer, Integer> getTopAndLeftFromTileName(String tileName) {

        String alpha = "ABCDEFGHIJKLMNOPQRSTUVWX";

        Integer index = alpha.indexOf(tileName);

        Integer row = index % IMAGE_COLS;
        Integer col = index / IMAGE_COLS;
        Integer leftX = row * TILE_HEIGHT;
        Integer topY = col * TILE_WIDTH;

        return new Pair<>(leftX, topY);
    }

    private static BufferedImage rotateClockwise(BufferedImage src, int n) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(n, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }

    private void createBoardPanel() {
        //Initialize the board to be a BOARD_ROWS x BOARD_COLS grid filled with dummyTiles
        for (int i = 0; i < BOARD_ROWS; i++) {
            ArrayList<JButton> row = new ArrayList<>();
            for (int j = 0; j < BOARD_COLS; j++) {
                //Dummy tiles indicate unfilled spots
                JButton button = new JButton();
                row.add(j, button);
            }
            this.boardTiles.add(row);
        }
        for (int i = 0; i < BOARD_ROWS; i++) {
            for (int j = 0; j < BOARD_COLS; j++) {
                Tile tile = this.carcassonne.getTileAtLocation(i, j);
                JButton tileButton = createTileButton(tile);
                this.boardTiles.get(i).set(j, tileButton);
                int row = i;
                int col = j;
                tileButton.addActionListener(e -> {
                    try {
                        if (!this.tilePlayedOnTurn) {
                            this.carcassonne.makeMove(this.carcassonne.getCurrentTile(), row, col);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(tileButton, ex.getMessage(), "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                    }
                });
                this.boardPanel.add(tileButton);
            }
        }
    }

    @Override
    public void tileRotated(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        this.sidePanel.remove(this.currentTileDisplay);
        this.currentTileDisplay = createCurrentTileDisplay();
        this.sidePanel.add(currentTileDisplay);
        this.sidePanel.revalidate();
        this.sidePanel.repaint();
    }

    @Override
    public void changeTurn(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        this.activePlayerLabel.setText(this.carcassonne.getActivePlayer() + "'s Turn");
        this.sidePanel.remove(this.currentTileDisplay);
        this.currentTileDisplay = createCurrentTileDisplay();
        this.sidePanel.add(currentTileDisplay);
        this.sidePanel.revalidate();
        this.sidePanel.repaint();

        this.tilePlayedOnTurn = false;
        this.followerNum = 0;

    }

    @Override
    public void changeScoreOrFollowers(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        ArrayList<Player> players = this.carcassonne.getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String playerStr = "<html>Player: " + player.getPlayerNumber() + "   ;   Score: " +
                    player.getScore().toString() + "<BR>" + "Followers Left: " +
                    player.followersLeft().toString() + "<html>";
            this.playerLabels.get(i).setText(playerStr);
        }

    }

    @Override
    public void changeTileCount(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        this.tilesLeftLabel.setText("Tiles Remaining: " + this.carcassonne.getTilesLeft().toString());
    }

    @Override
    public void changeBoard(Carcassonne carcassonne, int row, int col) {
        this.carcassonne = carcassonne;
        Tile activeTile = this.carcassonne.getCurrentTile();
        JButton tileButton = this.boardTiles.get(row).get(col);
        addImageToButton(activeTile, tileButton, -1, -1);

        this.tilePlayedOnTurn = true;
    }

    @Override
    public void changeFollowerLocation(Carcassonne carcassonne, int boardRow, int boardCol, int follRow, int follCol) {
        this.carcassonne = carcassonne;
        Tile activeTile = this.carcassonne.getCurrentTile();
        JButton tileButton = this.boardTiles.get(boardRow).get(boardCol);
        addImageToButton(activeTile, tileButton, follRow, follCol);

    }

    @Override
    public void endGame(Carcassonne carcassonne) {
        this.carcassonne = carcassonne;
        ArrayList<String> winners = this.carcassonne.getWinner();
        String message = "The winner/winners of the game: ";
        for (String name : winners) {
            message = message + name + ", ";
        }
        JOptionPane.showMessageDialog(this.getRootPane(), message, "Game Over",
                JOptionPane.INFORMATION_MESSAGE);
    }


}
