package edu.cmu.cs.cs214.hw4.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.Dimension;


import edu.cmu.cs.cs214.hw4.core.Carcassonne;


/**
 * A toy chat client designed to demonstrate basic GUI programming.
 */
public class CarcassonneClient extends JPanel {
    /**
     * Horizontal and vertical distance between successive chat windows in
     * pixels.
     */
    private static final int CHAT_WINDOW_POS_OFFSET = 30;

    /** The JFrame from which this chat is established. */
    private JFrame parentFrame;

    /** The names of the participants in this chat. */
    private Integer numPlayers = 2;


    public CarcassonneClient(JFrame frame) {
        this.parentFrame = frame;

        // Create the components to add to the panel.
        JLabel participantLabel = new JLabel("Input number of players: ");
        JLabel defaultMessage = new JLabel("Minimum Players : 2                           " +
                "                                      Maximum Players : 5");


        // Must be final to be accessible to the anonymous class.
        final JTextField participantText = new JTextField(20);

        JPanel participantPanel = new JPanel();
        participantPanel.setLayout(new BorderLayout());
        participantPanel.add(participantLabel, BorderLayout.WEST);
        participantPanel.add(participantText, BorderLayout.CENTER);
        participantPanel.add(defaultMessage, BorderLayout.SOUTH);


        // Defines an action listener to handle the addition of new participants
        ActionListener newParticipantListener = e -> {
            String num = participantText.getText().trim();
            if (!num.isEmpty()) {
                try {
                    numPlayers = Integer.parseInt(num);
                    if (numPlayers < 2 || numPlayers > 5) {
                        throw new IllegalArgumentException();
                    }
                    startGame(numPlayers);
                } catch (Exception ex) {
                    String message = "Please select 2, 3, 4, or 5 for the number of players";
                    JOptionPane.showMessageDialog(frame, message, "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
            participantText.setText("");
            participantText.requestFocus();
        };


        JButton createButton = new JButton("Start game");

        // notify the action listener when participant Button is pressed
        createButton.addActionListener(newParticipantListener);

        participantText.addActionListener(newParticipantListener);


        // Adds the components we've created to the panel (and to the window).
        setLayout(new BorderLayout());
        add(participantPanel, BorderLayout.NORTH);
        add(createButton, BorderLayout.SOUTH);
        setVisible(true);
    }

    /**
     * Starts a new chat, opening one window for each participant. Invoked on
     * EDT.
     */
    private void startGame(int numPlayers) {
        parentFrame.dispose();
        parentFrame = null;

        Carcassonne carcassonne = new Carcassonne(numPlayers);

        JFrame frame = new JFrame("Carcassonne");
        GameView gameView = new GameView(carcassonne);

        carcassonne.addGameChangeListener(gameView);

        frame.add(gameView);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1310, 1020));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}
