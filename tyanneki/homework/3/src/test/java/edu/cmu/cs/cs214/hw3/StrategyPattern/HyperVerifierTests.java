package edu.cmu.cs.cs214.hw3.StrategyPattern;

import org.junit.*;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

public class HyperVerifierTests {
    private HyperSolutionVerifier verifier;
    private HyperSolutionVerifier verifier2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid puzzle = new SudokuPuzzleGrid("src/main/resources/hypersudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/hypersudoku-solution-1.txt");
        verifier = new HyperSolutionVerifier(puzzle, sol);

        SudokuPuzzleGrid puzzle2 = new SudokuPuzzleGrid("src/main/resources/hypersudoku-problem-2.txt");
        SudokuPuzzleGrid sol2 = new SudokuPuzzleGrid("src/main/resources/hypersudoku-solution-2.txt");
        verifier2 = new HyperSolutionVerifier(puzzle2, sol2);
    }

    @Test
    public void testHyperIsValid() throws FileNotFoundException {
        assertTrue(verifier.isValidSolution());
        assertTrue(verifier2.isValidSolution());

        //Try with solution that is a valid hypersudoku solution but doesnt match the puzzle given
        SudokuPuzzleGrid puzzle;
        puzzle = new SudokuPuzzleGrid("src/main/resources/hypersudoku-problem-1.txt");
        SudokuPuzzleGrid sol2;
        sol2 = new SudokuPuzzleGrid("src/main/resources/hypersudoku-solution-2.txt");
        HyperSolutionVerifier verifier3 = new HyperSolutionVerifier(puzzle, sol2);
        assertFalse(verifier3.isValidSolution());

        //Try with a valid sudoku solution that isnt a valid hypersudoku solution
        SudokuPuzzleGrid puz = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid sol = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-2.txt");
        HyperSolutionVerifier verifier4 = new HyperSolutionVerifier(puz, sol);
        assertFalse(verifier4.isValidSolution());
    }


}