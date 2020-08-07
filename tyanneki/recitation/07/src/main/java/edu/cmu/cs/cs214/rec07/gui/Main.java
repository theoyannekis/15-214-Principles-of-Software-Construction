package edu.cmu.cs.cs214.rec07.gui;

import edu.cmu.cs.cs214.rec07.solver.StubSudokuFileVerifier;

import javax.swing.*;

/**
 * Entry point of your program
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            /*
               TODO:

               create an instance of your SudokuVerifierGui backed by a new instance of
               StubSudokuVerifier and make it visible. This requires a single line of code:

               https://docs.oracle.com/javase/8/docs/api/java/awt/Component.html#setVisible-boolean-

             */
            SwingUtilities.invokeLater(() -> new SudokuVerifierGui(new StubSudokuFileVerifier()));

             // Your code goes here
        });
    }
}
