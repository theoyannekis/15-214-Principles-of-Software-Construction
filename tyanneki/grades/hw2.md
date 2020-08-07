hw2 Feedback
============

#### Achieving design goals (25/25)

* Extensibility (10/10): Your representation for the sudoku puzzle grid and solution verifiers are sufficiently extensible, using polymorphism where appropriate.

* Reuse (10/10): Good job reusing the code!

* Information hiding (5/5): Good job using the principles of information hiding!


#### Java best practices and compatibility with our informal specification (39/40)

* Part 1 (15/15): The implementation of the sudoku puzzle grid works as expected. Nice!

* Part 2 (24/25):

  * -1, Instead of repeatedly calling the function `getBoard()`, you can call it once and store the board in in a temporary variable. ([link](https://github.com/CMU-17-214/tyanneki/blob/fdb59fe55cba55ecfda547b2bf195d09f591bace/homework/2/src/main/java/edu/cmu/cs/cs214/hw2/AsteriskSolutionVerifier.java#L33-L41)) 

#### Unit testing, including coverage and compliance with best practices (25/25)

* Quality of test suite (15/15): Your test suite extensively tests your code, including common cases, edge cases, and complex state changes.

* Testing best practices (10/10): Your test suite exhibits good use of best practices.

Very impressive test suite. What a joy to grade! 


#### Documentation and style (9/10)

  * -1, It's good practice to make clear that `XSolutionVerifier`, `AsteriskSolutionVerifier` and `SolutionVerifier` all implement the `SudokuSolutionVerifierInterface`. You only declared this in the basic verifier.

---

Well done!

#### Total (98/100) 

Late days used: LATE_DAYS_TAKEN (LATE_DAYS_LEFT left)

---

#### Additional Notes

Graded by: Rosie Sun (weijias1@andrew.cmu.edu)
