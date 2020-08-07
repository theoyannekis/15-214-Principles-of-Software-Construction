package edu.cmu.cs.cs214.hw3.StrategyPattern;

import java.util.List;

public class NumbrixSolutionVerifier implements SudokuSolutionVerifier {
    private static final int MAXNUM = 81;
    private static final int BOARDSIZE = 9;

    private SudokuPuzzleGrid puzzle;
    private SudokuPuzzleGrid propSol;

    /**
     * Creates a Numbrix Verifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    NumbrixSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        puzzle = puz;
        propSol = pSol;

    }


    /**
     * Checks if the proposed solution is a valid numbrix board. This is the case if the next number in the sequence
     * 1-81 is adjacent to the number prior. So 1 must be next to 2 and 3 must be next to 2 and so on.
     * @return boolean representing the validity of the proposed solution as a numbrix board
     */
    private boolean isValidNumbrix() {
        int left;
        int right;
        int above;
        int below;
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE ; j++) {
                //Get the current element and its surrounding elements. -1 indicates out of range
                int elem = ((getPropSol().getBoard()).get(i)).get(j);
                if (j - 1 >= 0)  left = ((getPropSol().getBoard()).get(i)).get(j - 1);
                else left = -1;
                if (j + 1 < BOARDSIZE) right = ((getPropSol().getBoard()).get(i)).get(j + 1);
                else right = -1;
                if (i - 1 >= 0) above = ((getPropSol().getBoard()).get(i - 1)).get(j);
                else above = -1;
                if (i + 1 < BOARDSIZE) below = ((getPropSol().getBoard()).get(i + 1)).get(j);
                else below = -1;
                int next = elem + 1;
                if (left != next && right != next && above != next && below != next && elem != MAXNUM) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Returns true if all of the non-zero elements in puzzle match with the corresponding spots in propSol
     * @return boolean representing if the non-zero ints in puzzle match those in propSol
     */
    boolean isMatch() {
        List<List<Integer>> puzzleGrid = puzzle.getBoard();
        List<List<Integer>> propSolGrid = propSol.getBoard();
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                int puzzleElem = (puzzleGrid.get(i)).get(j);
                int propSolElem = (propSolGrid.get(i)).get(j);
                if (puzzleElem != 0 && puzzleElem != propSolElem) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * To be a valid solution a proposed solution must match the given board and also correspond to the specification
     * of the puzzle. This function ensures both of those conditions.
     * @return boolean representing the validity of a proposed numbrix solution
     */
    public boolean isValidSolution() { return isMatch() && isValidNumbrix();
    }

    /**
     * Getter method to get the proposed Solution of a SudokuSolutionVerifier
     * @return the SudokuPuzzleGrid representing the proposed solution
     */
    SudokuPuzzleGrid getPropSol() {
        return propSol;
    }
}

