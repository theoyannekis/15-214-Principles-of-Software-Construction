package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;

/**
 * Class that creates an XSolutionVerifier which checks if a proposed solution is a valid solution
 * for a given Sudoku X puzzle
 */
public class GenericSolutionVerifier extends SudokuSolutionVerifier {

    private static final int BOARDSIZE = 9;

    /**
     * Creates a Generic Sudoku Verifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    GenericSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        super(puz, pSol);
    }

    /**
     * This function just returns true because it is only used inside the IsValidSudokuSolution method in the
     * superclass. That function contains all the functionality of a generic sudoku board
     * @return true
     */
    boolean isSpecificPuzzleType(){
        return true;
    }
}