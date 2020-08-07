package edu.cmu.cs.cs214.hw2;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a 9 x 9 sudoku grid. Receives the data on the board through a file.
 */
public class SudokuPuzzleGrid {

    private static final int BOARDSIZE = 9;

    private List<List<Integer>> board = new ArrayList<>();

    /**
     * Constucts a SudokuPuzzleGrid from a file passed into the constructor
     * @param txtFile text file name containing Sudouku board
     * @throws FileNotFoundException If an incorrect path is given
     * @throws IllegalArgumentException If an incorrectly formatted board is given. Too many rows, cols, etc.
     */
    public SudokuPuzzleGrid(String txtFile) throws FileNotFoundException, IllegalArgumentException {
        File file = new File(txtFile);

        Scanner input = new Scanner(file);

        while (input.hasNextLine()) {
            String line = input.nextLine();
            List<String> lst;
            lst = Arrays.asList(line.split(" "));
            List<Integer> intList = new ArrayList<>();
            for(String s : lst) {
                intList.add(Integer.valueOf(s));
            }
            board.add(intList);
        }
        //Check the board is correctly formatted
        if (board.size() != BOARDSIZE) {
            throw new IllegalArgumentException("Invalid Sudoku Board Given. All cols must be length 9");
        }
        for (int i = 0; i < BOARDSIZE; i++) {
            if (board.get(i).size() != BOARDSIZE) {
                throw new IllegalArgumentException("Invalid Sudoku Board given. All rows must be length 9");
            }
        }
    }

    /**
     * Getter method to get the board of a SudokuPuzzleGrid
     * @return the 2d array representing the Sudoku board
     */
    public List<List<Integer>> getBoard() {
        return board;
    }
}
