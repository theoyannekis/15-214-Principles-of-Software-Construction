package edu.cmu.cs.cs214.rec08;

import edu.cmu.cs.cs214.rec08.core.TicTacToeImpl;
import edu.cmu.cs.cs214.rec08.gui.TicTacToePanel;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGameBoard();
        });
    }

    private static void createAndShowGameBoard() {
        // TODO: Start implementing your GUI here.

        // Create a game core and do any necessary setup.
        TicTacToeImpl game = new TicTacToeImpl();
        // Create the game UI and setup. You will need to write
        // your own GUI classes under the "gui" package.
        TicTacToePanel gameGUI = new TicTacToePanel(game);
        JFrame frame = new JFrame();
        game.addGameChangeListener(gameGUI);
        frame.setContentPane(gameGUI);
        frame.setVisible(true);
        game.startNewGame();
    }

}
