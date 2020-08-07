package edu.cmu.cs.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.cmu.cs.cs214.hw4.core.Carcassonne;
import edu.cmu.cs.cs214.hw4.core.Player;

import javax.swing.*;

public class PlayerScoreDisplay extends JPanel {


    private static final float FONT_SIZE = 20.0f;

    private Carcassonne carcassonne;


    public PlayerScoreDisplay(Carcassonne carcassonne) {
        if (carcassonne == null) {
            throw new NullPointerException("carcassonne argument must not be null.");
        }
        this.carcassonne = carcassonne;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        ArrayList<Player> players = carcassonne.getPlayers();
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.BLUE);
        colors.add(Color.RED);
        colors.add(Color.MAGENTA);
        colors.add(Color.GREEN);
        colors.add(Color.GRAY);
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            String playerStr = "<html>Player: " + player.getPlayerNumber() + "   ;   Score: " +
                    player.getScore().toString() + "<BR>" + "Followers Left: " +
                    player.followersLeft().toString() + "<html>";
            final JLabel label = new JLabel(playerStr);
            label.setForeground(colors.get(i));
            label.setFont(label.getFont().deriveFont(FONT_SIZE));
            add(label);
        }

        setVisible(true);



    }

}
