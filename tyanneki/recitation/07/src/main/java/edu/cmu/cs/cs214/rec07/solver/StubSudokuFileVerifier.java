package edu.cmu.cs.cs214.rec07.solver;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The best sudoku verifier ever.
 * We lied; actually it returns a random answer.
 */
public class StubSudokuFileVerifier implements SudokuFileVerifier {
    public boolean verifySolution(String puzzleFile, String solutionFile) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
