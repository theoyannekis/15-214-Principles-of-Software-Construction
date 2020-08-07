package edu.cmu.cs.cs214.hw2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates an XSolutionVerifier which checks if a proposed solution is a valid solution
 * for a given Sudoku X puzzle
 */
public class XSolutionVerifier extends SudokuSolutionVerifier {

    private static final int BOARDSIZE = 9;

    /**
     * Creates a Sudoku X Verifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    public XSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        super(puz, pSol);
    }

    /**
     * Checks the validity of a proposed solution as a solution to a Sudoku X Board. This means that
     * those elements in the cells on each diagonal contain 1-9.
     * @return boolean representing the validity of the Sudoku X board
     */
    private boolean isValidX(){
        List<Integer> leftRightDiag = new ArrayList<>();
        List<Integer> rightLeftDiag = new ArrayList<>();
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                if (i == j) {
                    leftRightDiag.add((getPropSol().getBoard().get(i)).get(j));
                }
                if (i + j + 1 == BOARDSIZE) {
                    rightLeftDiag.add((getPropSol().getBoard().get(i)).get(j));
                }
            }
        }
        return increasingAndUnique(leftRightDiag) && increasingAndUnique(rightLeftDiag);
    }

    /**
     * Checks that proposed solution is a valid sudoku X, valid normal sudoku, and matches the puzzle
     * @return boolean representing the validity of the proposed solution as a solution for an X puzzle
     */
     @Override
    public boolean isValidSolution() {
        return super.isValidSolution() && isValidX();
    }
}
