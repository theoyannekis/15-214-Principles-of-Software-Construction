package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;


import java.util.ArrayList;
import java.util.List;

/**
 * Class that creates an AsteriskSolutionVerifier which checks if a proposed solution is a valid solution
 * for a given AsteriskSudoku puzzle
 */
public class AsteriskSolutionVerifier extends SudokuSolutionVerifier {

    private static final int MIDINDEX = 4;
    private static final int ORTHOGONALOFFSET = 3;
    private static final int DIAGOFFEST = 2;

    /**
     * Creates a AsteriskSolutionVerifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    AsteriskSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        super(puz, pSol);
    }

    /**
     * Checks the validity of a proposed solution as a solution to an Asterisk Board. This means that
     * those elements in the cells corresponding to the asterisk must be 1-9.
     * @return boolean representing validity of the Asterisk Sudoku solution
     */
    protected boolean isSpecificPuzzleType(){
        List<Integer> list = new ArrayList<>();
        //Get all the elements that make up the asterisk
        list.add((getPropSol().getBoard().get(MIDINDEX)).get(MIDINDEX));
        list.add((getPropSol().getBoard().get(MIDINDEX + ORTHOGONALOFFSET)).get(MIDINDEX));
        list.add((getPropSol().getBoard().get(MIDINDEX - ORTHOGONALOFFSET)).get(MIDINDEX));
        list.add((getPropSol().getBoard().get(MIDINDEX)).get(MIDINDEX + ORTHOGONALOFFSET));
        list.add((getPropSol().getBoard().get(MIDINDEX)).get(MIDINDEX - ORTHOGONALOFFSET));
        list.add((getPropSol().getBoard().get(MIDINDEX + DIAGOFFEST)).get(MIDINDEX + DIAGOFFEST));
        list.add((getPropSol().getBoard().get(MIDINDEX - DIAGOFFEST)).get(MIDINDEX + DIAGOFFEST));
        list.add((getPropSol().getBoard().get(MIDINDEX + DIAGOFFEST)).get(MIDINDEX - DIAGOFFEST));
        list.add((getPropSol().getBoard().get(MIDINDEX - DIAGOFFEST)).get(MIDINDEX - DIAGOFFEST));

        return increasingAndUnique(list);
    }

}