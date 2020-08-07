package edu.cmu.cs.cs214.hw2;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class that creates a SudokuSolutionVerifier with functionality to check the validity of a given
 * solution corresponding to a given sudoku puzzle. Works for multiple types of Sudoku including
 * normal Sudoku, Sudoku X, Hypersudoku, and Asterisk Sudoku
 */
public class SudokuSolutionVerifier implements SudokuSolutionVerifierInterface {

	private static final int BOARDSIZE = 9;
	private static final int BOXSIZE = 3;
	private static final int NUMARGS = 3;

	private SudokuPuzzleGrid puzzle;
	private SudokuPuzzleGrid propSol;

	/**
	 * The main method to run the Sudoku solution verifier. To run run the SudokuSolutionVerifier main method
	 * with 3 command line arguments (puzzleFile, solutionFile, puzzleType). Example 3 arguments would be
	 * "src/main/resources/hypersudoku-problem-1.txt" "src/main/resources/hypersudoku-solution-1.txt"
	 * "hyper".
	 * @param args	program arguments. First must be the file of the puzzle,
	 * and second must be the file of the proposed solution, then third is the puzzle type with possible
	 * types being "Sudoku", "Hyper", "X", and "Asterisk"
	 * @throws FileNotFoundException when an invalid file path is given
	 * @throws IllegalArgumentException when an invalid puzzleType is given or less than 3 arguments are given
	 */
    public static void main(String[] args) throws FileNotFoundException, IllegalArgumentException {
    	SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid(args[0]);
    	SudokuPuzzleGrid propSol = new SudokuPuzzleGrid(args[1]);
    	if (args.length < NUMARGS) {
            throw new IllegalArgumentException("Must have 3 command line arguments, File1, File2, " +
                    "puzzleType. PuzzleType may be 'Sudoku', 'Hyper', 'X', or 'Asterisk'");
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
            case "Sudoku": {
                SudokuSolutionVerifier verifier = new SudokuSolutionVerifier(puzzle, propSol);
                System.out.println(verifier.isValidSolution());
                break;
            }
			default: {
                throw new IllegalArgumentException("PuzzleType must be 'Sudoku', 'Hyper', 'X', or 'Asterisk'");
			}
		}

    }

    /**
     * Constructs a SudokuSolutionVerifier with two SudokuPuzzleGrids. One for puzzle and one for proposed solution
     * @param puz The SudokuPuzzleGrid representing the puzzle
     * @param pSol The SudokuPuzzleGrid representing the proposed solution
     */
    public SudokuSolutionVerifier(SudokuPuzzleGrid puz, SudokuPuzzleGrid pSol) {
        puzzle = puz;
        propSol = pSol;

	}

	/**
	 * Method used to ensure that the numbers 1-9 exist in a given list. Numbers must not appear twice.
	 * @param list the list of integers to decide if valid or not. Order of list passed in doesn't matter
	 * @return boolean corresponding to the validity of the list
	 */
	public boolean increasingAndUnique(List<Integer> list) {
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
     * First checks if the proposed solution is a valid Sudoku solution. Then checks if it matches the
     * puzzle board correctly. I.E. all the numbers in the puzzle match up with the corresponding slot in
	 * the solution
	 * @return boolean representing if the board is a valid solution to a conventional sudoku
	 * @implements isValidSolutionMethod in SudokuSolutionVerifierInterface
	 */
	public boolean isValidSolution() {
		for (int i = 0; i < BOARDSIZE; i++) {
			if (!isRowValid(i) || !isColValid(i) || !isBoxValid(i)) {
				return false;
			}
		}
		return isMatch();
	}

    /**
     * Returns true if all of the non-zero elements in puzzle match with the corresponding spots in propSol
     * @return boolean representing if the non-zero ints in puzzle match those in propSol
     */
	private boolean isMatch() {
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
	 * Getter method to get the proposed Solution of a SudokuSolutionVerifier
	 * @return the SudokuPuzzleGrid representing the proposed solution
	 */
	public SudokuPuzzleGrid getPropSol() {
		return propSol;
	}
}
