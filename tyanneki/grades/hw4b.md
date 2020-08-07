hw4b Feedback
============

#### Implementation of Carcassonne game (59/60)
* Representation of important classes (20/20), Your solution demonstrates a clear representation of domain concepts as data structures, with each component keeping track of sufficient information to perform its associated responsibilities.
* Tile placement and validation (15/15), Your solution demonstrates a correct and effective use of your representation of the board, tiles, and segments to perform tile validation and placement.
* Follower placement and validation (5/5), The implementation of follower placement and validation is correct or mostly correct.
* Scoring (19/20), The implementation of the scoring of features (and the associated state updates) is correct or mostly correct.
    * You use polymorphism to calculate feature scores, but still case on the feature type to change the number of "items" passed in to the calculateScore method. This is a missed opportunity for a truly great use of polymorphism which also increases the representational gap of the design. ([link](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Board.java#L367))

#### Program design (21/25), The design aspects of your implementation are mostly reasonable, but we have some suggestions for improvement.
* The methods that are part of the game's public API should be marked as public (things like starting the game, placing a tile and placing a follower). These will eventually be linked to buttons in your GUI but must be public in order to do so.
* changePlayerTurn is never called in the program, so I assume that it is meant to be part of the game's public API. However, after placing a tile and (optionally) placing a follower on it, the turn (and hence the scoring) should happen automatically, without having to ask the user ask the game to change the turn. ([link](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Carcassone.java#L76))

#### Testing and build automation (25/25)
* Testing practices (10/10), Your tests seem reasonable.
* Test coverage (10/10), Your testing coverage seems reasonable.
* Build automation (5/5), Build automation using gradle and Travis CI seems to work fine.

#### Documentation and style (5/10)
* -2, You should use the interface type where possible in variable declarations (e.g. `List` instead of `ArrayList`). This allows you to change which implementation of that interface is being used with minimal refactoring. The only exception is when you must access fields or methods present only on the narrower type. Read Effective Java item 50 for further information. ([link](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Carcassone.java#L15))
* -1, Please ensure that you have removed all commented out and/or dead code before submitting your project (This includes unused import statements, and print statements used for debugging). ([link](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Carcassone.java#L64))
* -1, The methods that throw exceptions don't have an explanation for when the exceptions get thrown in the appropriate javadoc (@throws). ([example](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Board.java#L67))
* -1, Using strings for equality checking is fragile and error prone. Consider using enums instead. ([link](https://github.com/CMU-17-214/tyanneki/blob/60e1eb82aafb3afc49381ec2226bbdd481e7bf54/homework/4/src/main/java/edu/cmu/cs/cs214/hw4/core/Tile.java#L151))

---

#### Total (110/120)

Late days used: 2 (3 left)

---

#### Additional Notes

Graded by: Diego Martinez (diegom@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/slade/blob/master/grades/hw4b.md
