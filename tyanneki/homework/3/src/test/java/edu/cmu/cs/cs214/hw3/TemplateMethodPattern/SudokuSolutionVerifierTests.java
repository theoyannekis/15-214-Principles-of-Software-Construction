package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;

import org.junit.Before;
import org.junit.Test;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SudokuSolutionVerifierTests {
    private GenericSolutionVerifier verifier;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-1.txt");
        verifier = new GenericSolutionVerifier(puzzle, sol);

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
                "src/main/resources/sudoku-solution-1.txt", "Generic"};
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
