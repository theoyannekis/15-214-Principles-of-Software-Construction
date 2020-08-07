package edu.cmu.cs.cs214.hw3.StrategyPattern;

import org.junit.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class NumbrixVerifierTests {
    private NumbrixSolutionVerifier verifier;
    private NumbrixSolutionVerifier verifier2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/numbrix-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/numbrix-solution-1.txt");
        verifier = new NumbrixSolutionVerifier(puzzle, sol);

        SudokuPuzzleGrid puzzle2 = new SudokuPuzzleGrid("src/main/resources/numbrix-problem-2.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/numbrix-solution-2.txt");
        verifier2 = new NumbrixSolutionVerifier(puzzle2, sol2);
    }

    @Test
    public void testisValidNumbrix() throws FileNotFoundException {
        assertTrue(verifier.isValidSolution());
        assertTrue(verifier2.isValidSolution());

        //Try with solution that is a valid Numbrix solution but doesn't match the puzzle given
        SudokuPuzzleGrid puzzle;
        puzzle = new SudokuPuzzleGrid("src/main/resources/numbrix-problem-1.txt");
        SudokuPuzzleGrid sol2;
        sol2 = new SudokuPuzzleGrid("src/main/resources/numbrix-solution-2.txt");
        NumbrixSolutionVerifier verifier3 = new NumbrixSolutionVerifier(puzzle, sol2);
        assertFalse(verifier3.isValidSolution());

        //Try with a valid sudoku solution that isnt a valid Numbrix solution
        SudokuPuzzleGrid puz = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        NumbrixSolutionVerifier verifier4 = new NumbrixSolutionVerifier(puz, sol);
        assertFalse(verifier4.isValidSolution());
    }


}