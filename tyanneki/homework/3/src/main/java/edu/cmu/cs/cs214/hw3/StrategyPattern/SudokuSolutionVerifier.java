package edu.cmu.cs.cs214.hw3.StrategyPattern;

/**
 * Interface for SudokuSolutionVerifiers
 */
public interface SudokuSolutionVerifier {
    /**
     * Tests if a proposed solution is a valid solution of the puzzle
     * @return boolean representing the validity of the proposed solution as a solution for the puzzle
     */
    boolean isValidSolution();

}
