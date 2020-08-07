package edu.cmu.cs.cs214.hw4.gui;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //add frame and set its closing operation
            JFrame frame = new JFrame("Carcassonne");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.add(new CarcassonneClient(frame));

            //display the JFrame
            frame.pack();
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
