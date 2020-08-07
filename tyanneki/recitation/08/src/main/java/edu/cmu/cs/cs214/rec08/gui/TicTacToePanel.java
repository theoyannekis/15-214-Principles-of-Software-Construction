package edu.cmu.cs.cs214.rec08.gui;

import edu.cmu.cs.cs214.rec08.core.GameChangeListener;
import edu.cmu.cs.cs214.rec08.core.TicTacToe;
import edu.cmu.cs.cs214.rec08.core.TicTacToeImpl;

import javax.swing.*;
import java.awt.*;

/**
 *
 */
public class TicTacToePanel extends JPanel implements GameChangeListener{
    private final TicTacToeImpl game;

    private final JLabel currentPlayerLabel;
    private final JButton[][] squares;

    /**
     *
     * @param g the game
     */
    public TicTacToePanel(TicTacToeImpl g) {
        game = g;

        currentPlayerLabel = new JLabel();
        squares = new JButton[TicTacToe.GRID_SIZE][TicTacToe.GRID_SIZE];
        setLayout(new BorderLayout());
        add(currentPlayerLabel,BorderLayout.NORTH);
        add(createBoard(),BorderLayout.CENTER);
    }

    private JPanel createBoard() {
        JPanel board = new JPanel();
        board.setLayout(new GridLayout(TicTacToe.GRID_SIZE,TicTacToe.GRID_SIZE));
        for(int r = 0; r < TicTacToe.GRID_SIZE; r++) {
            for(int c = 0; c < TicTacToe.GRID_SIZE; c++) {
                JButton button = new JButton();
                squares[r][c] = button;
                int row = r;
                int col = c;
                button.addActionListener(e -> {
                    game.playMove(row,col);
                });
                board.add(button);
            }
        }
        return board;
    }

    @Override
    public void squareChanged(int row, int col) {
        TicTacToe.Player x = game.getSquare(row, col);
        if(x != null) {
            squares[row][col].setText(x.name());
        } else {
            squares[row][col].setText("");
        }
    }

    @Override
    public void currentPlayerChanged(TicTacToe.Player player) {
        currentPlayerLabel.setText(player.name());
    }

    @Override
    public void gameEnded(TicTacToe.Player winner) {
        JFrame frame = (JFrame) SwingUtilities.getRoot(this);
        if(winner == null) {
            JOptionPane.showMessageDialog(frame,"Tie!", "Tie!",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, winner.name()+" wins!","Winner!",JOptionPane.INFORMATION_MESSAGE);
        }

    }
}
