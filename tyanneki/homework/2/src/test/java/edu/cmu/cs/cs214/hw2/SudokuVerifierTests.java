package edu.cmu.cs.cs214.hw2;

import org.junit.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SudokuVerifierTests {
    private SudokuSolutionVerifier verifier;
    private SudokuSolutionVerifier verifier2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-1.txt");
        verifier = new SudokuSolutionVerifier(puzzle, sol);

        SudokuPuzzleGrid puzzle2 = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-2.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        verifier2 = new SudokuSolutionVerifier(puzzle2, sol2);
    }

    @Test
    public void testIsValid() throws FileNotFoundException {
        assertTrue(verifier.isValidSolution());
        //Set one value that's a 7 to an 8.
        verifier.getPropSol().getBoard().get(0).set(1,8);
        assertFalse(verifier.isValidSolution());
        verifier.getPropSol().getBoard().get(0).set(1,7);
        assertTrue(verifier.isValidSolution());
        //Other normal sudoku puzzle
        assertTrue(verifier2.isValidSolution());

        //Try with solution that is a valid sudoku solution but doesnt match the puzzle given
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        SudokuSolutionVerifier verifier3 = new SudokuSolutionVerifier(puzzle, sol2);
        assertFalse(verifier3.isValidSolution());

    }

    @Test
    public void testIncreasingAndUnique() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            list.add(i-1, i);
        }
        assertTrue(verifier.increasingAndUnique(list));
        list.set(2, 2);
        assertFalse(verifier.increasingAndUnique(list));
        list.set(1,3);
        assertTrue(verifier.increasingAndUnique(list));
    }

    @Test
    public void testMainAsterisk() throws FileNotFoundException {
        String[] args = {"src/main/resources/asterisksudoku-problem-1.txt",
                "src/main/resources/asterisksudoku-solution-1.txt", "Asterisk"};
        SudokuSolutionVerifier.main(args);
    }

    @Test
    public void testMainX() throws FileNotFoundException {
        String[] args = {"src/main/resources/sudoku-x-problem-1.txt",
                "src/main/resources/sudoku-x-solution-1.txt", "X"};
        SudokuSolutionVerifier.main(args);
    }

    @Test
    public void testMainHyper() throws FileNotFoundException {
        String[] args = {"src/main/resources/hypersudoku-problem-1.txt",
                "src/main/resources/hypersudoku-solution-1.txt", "Hyper"};
        SudokuSolutionVerifier.main(args);
    }

    @Test
    public void testMainSudoku() throws FileNotFoundException {
        String[] args = {"src/main/resources/sudoku-problem-1.txt",
                "src/main/resources/sudoku-solution-1.txt", "Sudoku"};
        SudokuSolutionVerifier.main(args);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testMainSudokuWrongPuzzleType() throws FileNotFoundException, IllegalArgumentException {
        String[] args = {"src/main/resources/sudoku-problem-1.txt",
                "src/main/resources/sudoku-solution-1.txt", "invalid"};
        SudokuSolutionVerifier.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMainSudokuTooFewArgs() throws FileNotFoundException, IllegalArgumentException {
        String[] args = {"src/main/resources/sudoku-problem-1.txt",
                "src/main/resources/sudoku-solution-1.txt"};
        SudokuSolutionVerifier.main(args);
    }

}
