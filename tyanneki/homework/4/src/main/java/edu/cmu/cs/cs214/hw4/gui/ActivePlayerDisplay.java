package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.Player;

import javax.swing.*;

public class ActivePlayerDisplay extends JPanel {


    private static final float FONT_SIZE = 20.0f;

    private Carcassonne carcassonne;


    public ActivePlayerDisplay(Carcassonne carcassonne) {
        if (carcassonne == null) {
            throw new NullPointerException("carcassonne argument must not be null.");
        }
        this.carcassonne = carcassonne;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        Player player = carcassonne.getActivePlayer();

        String playerStr = "Player " + player.getPlayerNumber() + "'s Turn";
        final JLabel label = new JLabel(playerStr);
        label.setFont(label.getFont().deriveFont(FONT_SIZE));
        add(label);

        setVisible(true);



    }

}
