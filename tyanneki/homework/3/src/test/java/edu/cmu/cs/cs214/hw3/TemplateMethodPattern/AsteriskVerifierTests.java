package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;

import org.junit.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class AsteriskVerifierTests {
    private AsteriskSolutionVerifier verifier;
    private AsteriskSolutionVerifier verifier2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-solution-1.txt");
        verifier = new AsteriskSolutionVerifier(puzzle, sol);

        SudokuPuzzleGrid puzzle2 = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-problem-2.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-solution-2.txt");
        verifier2 = new AsteriskSolutionVerifier(puzzle2, sol2);
    }

    @Test
    public void testAsteriskIsValid() throws FileNotFoundException {
        assertTrue(verifier.isValidSudokuSolution());
        assertTrue(verifier2.isValidSudokuSolution());

        //Try with solution that is a valid asterisksudoku solution but doesnt match the puzzle given
        SudokuPuzzleGrid puzzle;
        puzzle = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-problem-1.txt");
        SudokuPuzzleGrid sol2;
        sol2 = new SudokuPuzzleGrid("src/main/resources/asterisksudoku-solution-2.txt");
        AsteriskSolutionVerifier verifier3 = new AsteriskSolutionVerifier(puzzle, sol2);
        assertFalse(verifier3.isValidSudokuSolution());

        //Try with a valid sudoku solution that isnt a valid asterisksudoku solution
        SudokuPuzzleGrid puz = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        AsteriskSolutionVerifier verifier4 = new AsteriskSolutionVerifier(puz, sol);
        assertFalse(verifier4.isValidSudokuSolution());
    }


}