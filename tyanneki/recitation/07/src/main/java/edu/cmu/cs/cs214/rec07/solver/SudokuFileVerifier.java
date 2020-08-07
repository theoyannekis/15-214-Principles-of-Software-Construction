package edu.cmu.cs.cs214.rec07.solver;

/**
 * Verifies whether a proposed solution is a solution to a basic Sudoku puzzle.
 */
public interface SudokuFileVerifier {
    /**
     * Given the file names of a puzzle and proposed solution, returns true if
     * the proposed solution is a solution of the puzzle.
     *
     * @return true if the proposed solution is a solution to the puzzle
     * @param puzzleFile  file name of the puzzle
     * @param solutionFile file name of the proposed solution
     */
    boolean verifySolution(String puzzleFile, String solutionFile);
}
