package edu.cmu.cs.cs214.hw3.StrategyPattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that creates an GenericSudoku Solution Verifier which checks if a proposed solution is a valid solution
 * for a given Sudoku puzzle. Implements the SudokuSolutionVerifier Interface
 */
public class GenericSolutionVerifier implements SudokuSolutionVerifier {

    private static final int BOARDSIZE = 9;
    private static final int BOXSIZE = 3;

    private SudokuPuzzleGrid puzzle;
    private SudokuPuzzleGrid propSol;


    /**
     * Creates a Generic Sudoku Verifier
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    public GenericSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        puzzle = puz;
        propSol = pSol;

    }

    /**
     * Method used to ensure that the numbers 1-9 exist in a given list. Numbers must not appear twice.
     * @param list the list of integers to decide if valid or not. Order of list passed in doesn't matter
     * @return boolean corresponding to the validity of the list
     */
     boolean increasingAndUnique(List<Integer> list) {
        //Start by copying list to prevent aliasing
        List<Integer> copy = new ArrayList(list);
        Collections.sort(copy);
        for (int i = 0; i < BOARDSIZE; i++) {
            int elem = copy.get(i);
            //Check that 0th elem is 1, 1st elem is 2, etc. This checks that 1-size are present once.
            if (elem != i + 1) {
                return false;
            }
        }
        return true;

    }
    /**
     * Checks the validity of a row in the sudoku board. Valid if it contains 1-9 in any order
     * @param rowNum the index of the row in the sudoku board
     * @return boolean corresponding to the validity of the row
     */
    private boolean isRowValid(int rowNum) {

        List<Integer> row = (propSol.getBoard()).get(rowNum);
        return increasingAndUnique(row);
    }

    /**
     * Checks the validity of a column in the sudoku board. Valid if it contains 1-9 in any order
     * @param colNum the number of the column in the board to check
     * @return boolean corresponding to the validity of the column
     */
    private boolean isColValid(int colNum) {
        List<Integer> col = new ArrayList<>();
        for (int i = 0; i < BOARDSIZE; i++) {
            //Get the colNumth elem of the ith row of the board
            int elem = ((propSol.getBoard()).get(i)).get(colNum);
            col.add(elem);
        }
        return increasingAndUnique(col);
    }

    /**
     * Checks the validity of a box in the sudoku board. Valid if contains 1-9
     * @param boxNum the number of the box on the board to check. 0 indexed
     * @return boolean corresponding to the validity of the box
     */
    private boolean isBoxValid(int boxNum) {
        int startRow = BOXSIZE * (boxNum / BOXSIZE);
        int startCol = BOXSIZE * (boxNum % BOXSIZE);
        List<Integer> list = new ArrayList<>();
        for (int i = startRow; i < startRow + BOXSIZE; i++) {
            for (int j = startCol; j < startCol + BOXSIZE; j++) {
                int elem = ((propSol.getBoard()).get(i)).get(j);
                list.add(elem);
            }
        }
        return increasingAndUnique(list);
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
     * Checks if the proposed solution is a valid solution of the generic sudoku puzzle
     * @return boolean representing the validity of a proposed solution as a solution to the generic sudoku board
     */
    public boolean isValidSolution(){
        for (int i = 0; i < BOARDSIZE; i++) {
            if (!isRowValid(i) || !isColValid(i) || !isBoxValid(i)) {
                return false;
            }
        }
        return isMatch();
    }

    /**
     * Getter method to get the proposed Solution of a SudokuSolutionVerifier
     * @return the SudokuPuzzleGrid representing the proposed solution
     */
    SudokuPuzzleGrid getPropSol() {
        return propSol;
    }
}

