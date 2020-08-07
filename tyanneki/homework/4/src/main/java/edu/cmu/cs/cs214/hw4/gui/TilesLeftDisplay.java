package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.Player;

import javax.swing.*;

public class TilesLeftDisplay extends JPanel {


    private static final float FONT_SIZE = 20.0f;

    private Carcassonne carcassonne;


    public TilesLeftDisplay(Carcassonne carcassonne) {
        if (carcassonne == null) {
            throw new NullPointerException("carcassonne argument must not be null.");
        }
        this.carcassonne = carcassonne;

        Integer tilesLeft = carcassonne.getTilesLeft();

        String tilesStr = "Tiles Remaining: " + tilesLeft.toString();
        final JLabel label = new JLabel(tilesStr);
        label.setFont(label.getFont().deriveFont(FONT_SIZE));
        add(label);

        setVisible(true);



    }

}
