package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;


import edu.cmu.cs.cs214.hw4.core.Tile;
import javafx.util.Pair;

import javax.imageio.ImageIO;
import javax.swing.*;

public class TileDisplay extends JPanel {

    private static final int TILE_WIDTH = 90;
    private static final int TILE_HEIGHT = 90;
    private static final int IMAGE_COLS = 6;

    private Tile tile;


    public TileDisplay(Tile tile) {
        if (tile == null) {
            throw new NullPointerException("tile argument must not be null.");
        }
        this.tile = tile;

        JButton tileButton = new JButton();

        Dimension panelD = new Dimension(TILE_WIDTH,TILE_HEIGHT);
        tileButton.setPreferredSize(panelD);

        if (!this.tile.isDummyTile()) {
            try {
                BufferedImage image = ImageIO.read(new File("src/main/resources/Carcassonne.png"));

                Pair<Integer, Integer> xAndY = getTopAndLeftFromTileName(this.tile.getTileName());
                int x = xAndY.getKey();
                int y = xAndY.getValue();
                BufferedImage tileImage = image.getSubimage(x, y, TILE_WIDTH, TILE_HEIGHT);
                //Now rotate the image
                tileImage = rotateClockwise(tileImage, 0);
                ImageIcon icon = new ImageIcon(tileImage);
                tileButton.setIcon(icon);
            } catch (Exception e) {
                System.out.println("COULD NOT FIND FILE FOR TILE IMAGE");
                String message = "Please select 2, 3, 4, or 5 for the number of players";
                JOptionPane.showMessageDialog(tileButton, message, "Invalid Input",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        add(tileButton);

        setVisible(true);



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

        Integer row = index / IMAGE_COLS;
        Integer col = index % IMAGE_COLS;
        Integer leftX = row * TILE_WIDTH;
        Integer topY = col * TILE_HEIGHT;

        return new Pair<>(leftX, topY);
    }

    public static BufferedImage rotateClockwise(BufferedImage src, int n) {
        int weight = src.getWidth();
        int height = src.getHeight();

        AffineTransform at = AffineTransform.getQuadrantRotateInstance(n, weight / 2.0, height / 2.0);
        AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);

        BufferedImage dest = new BufferedImage(weight, height, src.getType());
        op.filter(src, dest);
        return dest;
    }


}
