package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;

import org.junit.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class GenericVerifierTests {
    private GenericSolutionVerifier verifier;
    private GenericSolutionVerifier verifier2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-1.txt");
        verifier = new GenericSolutionVerifier(puzzle, sol);

        SudokuPuzzleGrid puzzle2 = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-2.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        verifier2 = new GenericSolutionVerifier(puzzle2, sol2);
    }

    @Test
    public void testIsValid() throws FileNotFoundException {
        assertTrue(verifier.isValidSudokuSolution());
        //Set one value that's a 7 to an 8.
        verifier.getPropSol().getBoard().get(0).set(1,8);
        assertFalse(verifier.isValidSudokuSolution());
        verifier.getPropSol().getBoard().get(0).set(1,7);
        assertTrue(verifier.isValidSudokuSolution());
        //Other normal sudoku puzzle
        assertTrue(verifier2.isValidSudokuSolution());

        //Try with solution that is a valid sudoku solution but doesnt match the puzzle given
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        GenericSolutionVerifier verifier3 = new GenericSolutionVerifier(puzzle, sol2);
        assertFalse(verifier3.isValidSudokuSolution());

    }


}