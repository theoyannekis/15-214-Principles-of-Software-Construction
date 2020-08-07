package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.Tile;

import javax.swing.*;

public class CurrentTileDisplay extends JPanel {


    private static final float FONT_SIZE = 20.0f;

    private Carcassonne carcassonne;


    public CurrentTileDisplay(Carcassonne carcassonne) {
        if (carcassonne == null) {
            throw new NullPointerException("carcassonne argument must not be null.");
        }
        this.carcassonne = carcassonne;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        String tileStr = "Current Tile: ";
        final JLabel label = new JLabel(tileStr);
        label.setFont(label.getFont().deriveFont(FONT_SIZE));
        Tile currentTile = carcassonne.getCurrentTile();
        TileDisplay tileDisplay = new TileDisplay(currentTile);
        JButton rotateButton = new JButton();
        rotateButton.setText("Rotate Tile");
        rotateButton.addActionListener(e -> {
            this.carcassonne.rotateTile(currentTile, 1);
        });

        add(label);
        add(tileDisplay);
        add(rotateButton);

        setVisible(true);



    }

}
