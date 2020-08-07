package edu.cmu.cs.cs214.hw2;

/**
 * Interface for SudokuSolutionVerifier
 */
public interface SudokuSolutionVerifierInterface {
    /**
     * Checks if the proposed solution both is a valid Sudoku solution and matches the puzzle given
     * @return boolean corresponding to the validity of the sudoku grid
     */
    boolean isValidSolution();
}
