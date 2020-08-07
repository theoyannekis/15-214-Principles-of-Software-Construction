package edu.cmu.cs.cs214.rec07.gui;

import edu.cmu.cs.cs214.rec07.solver.SudokuFileVerifier;

import javax.swing.*;
import java.awt.*;

/**
 * Gui interface for SudokuFileVerifier.
 */
public class SudokuVerifierGui extends JFrame {
    //Class constants
    private static final String TITLE = "Basic sudoku puzzle verifier";

    //GUI components
    private InputPanel inputPanel;
    private JTextArea solutionArea;

    private final SudokuFileVerifier fileVerifier;

    public SudokuVerifierGui(SudokuFileVerifier fileVerifier) {
        /* TODO:

           Do the following in this constructor. Each of these steps requires one (or in the case of step 6, two)
           lines of code. See the indicated web pages for more information:

           1. Call our superClass constructor with title TITLE:
              https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html

           2. Set our default close operation to EXIT_ON_CLOSE, ensuring that the app exits when the frame is closed.
              https://docs.oracle.com/javase/7/docs/api/javax/swing/JFrame.html#setDefaultCloseOperation(int)

           3. Initialize inputPanel with a new instance of the InputPanel class defined below.

           4. Initialize solutionArea to a new JTextArea.
              https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextArea.html

           5. Set the layout manager for this frame to a new BoxLayout manager. The API for this layout manager
              is pretty bad, so we'll give you a little hint:
                  setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
              information: http://docs.oracle.com/javase/tutorial/uiswing/layout/using.html

           6. add inputPanel and solutionArea to frame.
              https://docs.oracle.com/javase/8/docs/api/javax/swing/JFrame.html

           7. Call the pack method on the frame, causing the frame to lay out the components.
              Note that JFrame is a subtype of java.awt.Window
              https://docs.oracle.com/javase/7/docs/api/java/awt/Window.html#pack()

         */

        // Your code for steps 1-7 goes here.
        super(TITLE);
        this.fileVerifier = fileVerifier;
        solutionArea = new JTextArea("True or false is printed here.");
        inputPanel = new InputPanel();
        addGuiComponents();
    }

    private void addGuiComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
        add(inputPanel);
        add(solutionArea);
        setVisible(true);
        setResizable(true);
        pack();
    }

    private class InputPanel extends JPanel {
        private static final String VERIFY_BUTTON_TEXT = "Verify";
        private static final String PUZZLE_PROMPT = "puzzle file name";
        private static final String SOLUTION_PROMPT = "solution file name";
        private static final int INPUT_FIELD_WIDTH = 40;
        private JButton verifyButton;
        private JTextField puzzleInput;
        private JTextField solutionInput;

        InputPanel() {
            /* TODO:

              Each step requires one (or in the case of step 4, two) lines of code):

              1. Initialize the JButton field (verifyButton) with a new JButton with VERIFY_BUTTON_TEXT as its label text.
                 https://docs.oracle.com/javase/8/docs/api/javax/swing/JButton.html

              2. Initialize the JTextFields (puzzleInput and solutionInput) with new JTextFields with
                 PUZZLE_PROMPT and SOLUTION_PROMPT as their initial text, and INPUT_FIELD_WIDTH as their initial width.
                 https://docs.oracle.com/javase/8/docs/api/javax/swing/JTextField.html

              3. Add an action listener of the JButton to be a function that sets the text of solutionArea
                 to display whether or not the solution file contains a valid solution to the puzzle.
                 Note that you can update that field from this constructor,
                 as this class is a non-static nested class of SudokuVerifierGui.
                 Use a lambda for the ActionListener if you know how; otherwise, use an anonymous class instance.
                 https://docs.oracle.com/javase/tutorial/uiswing/components/button.html
                 https://docs.oracle.com/javase/8/docs/api/javax/swing/text/JTextComponent.html#setText-java.lang.String-

              4. Add the three components (verifyButton and the input fields) to this panel. Note that a JPanel is a
                 java.awt.Container.
                 https://docs.oracle.com/javase/8/docs/api/java/awt/Container.html#add-java.awt.Component-

            */

            // Your code for steps 1-4 goes here.
            verifyButton = new JButton(VERIFY_BUTTON_TEXT);
            puzzleInput = new JTextField(PUZZLE_PROMPT, INPUT_FIELD_WIDTH);
            solutionInput = new JTextField(SOLUTION_PROMPT, INPUT_FIELD_WIDTH);
            verifyButton.addActionListener(event -> {
                String puzzleFileName = puzzleInput.getText();
                String solutionFileName = solutionInput.getText();
                boolean isSolution = fileVerifier.verifySolution(puzzleFileName, solutionFileName);
                solutionArea.setText("The proposed solution is " + (isSolution ? "" : "not ") + "a solution.");
            });
            addInputPanelComponents();
        }

        private void addInputPanelComponents() {
            setLayout(new FlowLayout());
            puzzleInput.setPreferredSize(new Dimension(INPUT_FIELD_WIDTH, 20));
            add(puzzleInput);
            solutionInput.setPreferredSize(new Dimension(INPUT_FIELD_WIDTH, 20));
            add(solutionInput);
            add(verifyButton);
            setVisible(true);
        }
    }
}
