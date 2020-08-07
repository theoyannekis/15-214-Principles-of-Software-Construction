package edu.cmu.cs.cs214.hw3.StrategyPattern;

import java.io.FileNotFoundException;

/**
 * Class that creates a SudokuSolutionVerifier with functionality to check the validity of a given
 * solution corresponding to a given sudoku puzzle. Works for multiple types of Sudoku including
 * Generic, Sudoku X, Hypersudoku, Numbrix, and Asterisk Sudoku
 */
public class SudokuVerifierMain {

    private static final int NUMARGS = 3;

    /**
     * The main method to run the Sudoku solution verifier. To run run the SudokuSolutionVerifier main method
     * with 3 command line arguments (puzzleFile, solutionFile, puzzleType). Example 3 arguments would be
     * "src/main/resources/hypersudoku-problem-1.txt" "src/main/resources/hypersudoku-solution-1.txt"
     * "hyper".
     *
     * @param args program arguments. First must be the file of the puzzle,
     *             and second must be the file of the proposed solution, then third is the puzzle type with possible
     *             types being "Generic", "Hyper", "X", "Numbrix", and "Asterisk"
     * @throws FileNotFoundException    when an invalid file path is given
     * @throws IllegalArgumentException when an invalid puzzleType is given or less than 3 arguments are given
     */
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid(args[0]);
        SudokuPuzzleGrid propSol = new SudokuPuzzleGrid(args[1]);
        if (args.length < NUMARGS) {
            throw new IllegalArgumentException("Must have 3 command line arguments, File1, File2, " +
                    "puzzleType. PuzzleType may be 'Generic', 'Hyper', 'X', 'Numbrix', or 'Asterisk'");
        }
        String puzzleType = args[2];
        switch (puzzleType) {
            case "Asterisk": {
                AsteriskSolutionVerifier verifier = new AsteriskSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
            case "X": {
                XSolutionVerifier verifier = new XSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
            case "Hyper": {
                HyperSolutionVerifier verifier = new HyperSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
            case "Generic": {
                GenericSolutionVerifier verifier = new GenericSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
            case "Numbrix": {
                NumbrixSolutionVerifier verifier = new NumbrixSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
            default: {
                throw new IllegalArgumentException("PuzzleType must be 'Generic', 'Hyper', 'X', 'Numbrix', or 'Asterisk'");
            }
        }

    }
}