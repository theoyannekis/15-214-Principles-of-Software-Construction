hw3 Feedback
============

#### Demonstrate mastery of earlier learning goals, especially the concepts of information hiding and polymorphism, software design based on informal specifications, and Java coding, specification, testing practices and style. (39/40)

* Information hiding (10/10)
  * Good job for using information hiding!
* Compliance with specification (20/20)
  * Good job implementing Numbrix according to specification!
* Testing practices (5/5)
  * Good Testing!
* Java coding best practices and style (4/5)
  * -1, Please document your public classes/methods with comments. You are missing documentation in several classes. Building your code with gradle and checking Travis-CI are good ideas because they reveal checkstyle errors. ([Mssing Class](https://github.com/CMU-17-214/tyanneki/blob/b8212d3998a7136b934522b57169a3d55cfa0505/homework/3/src/main/java/edu/cmu/cs/cs214/hw3/StrategyPattern/NumbrixSolutionVerifier.java#L5))

#### Use inheritance, delegation, and design patterns effectively to achieve design flexibility and code reuse (35/40)

* Demonstrates understanding of the template method pattern (15/15)
  * Good job using the template method pattern!

* Demonstrates understanding of the strategy pattern (15/15)
  * Good job using the strategy pattern!

* Achieves flexibility and code reuse (5/10)
  * Your implementations of the interface for the strategy pattern re-implement the same basic sudoku verifier code for each sudoku invariant. Better code reuse could have been achieved by delegation or inheritance.
  * You have duplicated tests for the strategy and template method patterns. You should be able to test the same underlying behavior with the same tests.

#### Discuss the relative advantages and disadvantages of alternative design choices (17/20)

* Part 1 (12/15)
  * Your argument conflates weaknesses of your implementation with weaknesses of the design pattern itself. For example, the strategy pattern does not imply poor code re-use or remove the ability to use helper methods. 
* Part 2 (5/5)
  * Good Discussion on second part!
---

#### Total (91/100)

Late days used: 0 (5 left)

---

#### Additional Notes

Graded by: Caroline Sun (jiatians@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/tyanneki/blob/master/grades/hw3.md

