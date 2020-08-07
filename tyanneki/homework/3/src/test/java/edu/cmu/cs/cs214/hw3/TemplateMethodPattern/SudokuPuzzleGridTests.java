package edu.cmu.cs.cs214.hw3.TemplateMethodPattern;



import org.junit.*;
import java.io.FileNotFoundException;

public class SudokuPuzzleGridTests {
    private SudokuPuzzleGrid grid1;
    private SudokuPuzzleGrid grid2;

    @Before
    public void setup() throws FileNotFoundException {
        SudokuPuzzleGrid grid1 = new SudokuPuzzleGrid("src/main/resources/sudoku-problem-1.txt");
        SudokuPuzzleGrid grid2 = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-1.txt");
    }

    @Test(expected = FileNotFoundException.class)
    public void testInvalidFile() throws FileNotFoundException {
        SudokuPuzzleGrid badGrid = new SudokuPuzzleGrid("src/main/resources/sudoku-solution-5.txt");

    }

    @Test(expected = IllegalArgumentException.class)
    public void testWideBoard() throws IllegalArgumentException, FileNotFoundException{
        SudokuPuzzleGrid badGrid = new SudokuPuzzleGrid("src/main/resources/wide-sudoku-board.txt");

    }
    @Test(expected = IllegalArgumentException.class)
    public void testTallBoard() throws IllegalArgumentException, FileNotFoundException{
        SudokuPuzzleGrid badGrid = new SudokuPuzzleGrid("src/main/resources/tall-sudoku-board.txt");

    }


}