package edu.cmu.cs.cs214.hw2;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates an HyperSolutionVerifier which checks if a proposed solution is a valid solution
 * for a given HyperSudoku puzzle
 */
public class HyperSolutionVerifier extends SudokuSolutionVerifier {

    private static final int BOXSIZE = 3;
    private static final int BOXOFFSET = 5;

    /**
     * Creates a Hyper Sudoku Verifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    public HyperSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        super(puz, pSol);
    }

    private boolean isHyperBoxValid(int startRow, int startCol) {
        List<Integer> list = new ArrayList<>();
        for (int i = startRow; i < startRow + BOXSIZE; i++) {
            for (int j = startCol; j < startCol + BOXSIZE; j++) {
                int elem = ((getPropSol().getBoard()).get(i)).get(j);
                list.add(elem);
            }
        }
        return increasingAndUnique(list);
    }

    /**
     * Checks the validity of a proposed solution as a solution to a Hyper Sudoku Board. This means that
     * those elements in the cells of those 4 extra boxes must be 1-9.
     * @return boolean representing validity of Hyper Sudoku solution
     */
    private boolean isValidHyper() {
        return isHyperBoxValid(1, 1) &&
                isHyperBoxValid(1, BOXOFFSET) &&
                isHyperBoxValid(BOXOFFSET, 1) &&
                isHyperBoxValid(BOXOFFSET, BOXOFFSET);
    }

    /**
     * Checks that proposed solution is a valid hyper, valid normal sudoku, and matches the puzzle
     * @return boolean representing the validity of the proposed solution as a solution for an X puzzle
     */
    @Override
    public boolean isValidSolution() {
        return super.isValidSolution() && isValidHyper();
    }
}
