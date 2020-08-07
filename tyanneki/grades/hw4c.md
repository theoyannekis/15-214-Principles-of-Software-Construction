hw4c Feedback
============


### Design of GUI (30/30)

##### Separation of GUI and Core (20/20)
* Your implementation effectively separates the GUI from the core implementation.

##### General GUI design (10/10)
* You effectively applied best practices of GUI design.

### Implementation of GUI (22/40)

##### Appropriate handling of events using the observer pattern (10/10)
* Your GUI correctly handled the various board state change events. Well done!

##### GUI Gameplay (5/20)
* Your GUI contains some of the key aspects of Carcassonne gameplay, but we identified the following major issues:
    * The GUI is _highly_ unresponsive and laggy due to it having so many JButtons. Instead, a better approach would have been to only have the necessary buttons (those with valid placements or at the edge/frontier of the board).
    * Users aren't given the choice to place followers. Instead, a follower is placed on an arbitrary segment in the tile.
    * The discard tile button should only be enabled when there is no valid tile placement. Players should not be able to discard a tile whenever they want.
    * When features get completed, followers are returned to players but they aren't removed from the tile images, which makes it seem as though they don't get returned.

##### Build Automation using Travis and Gradle (3/5)
* -2, We were unable to start your game using `gradle run`, as required in the handout.

##### Documentation + Style (5/5)
* Your implementation meets our expectations regarding documentation and style.

### Reflection on the design process (10/10)
* You submitted a design change discussion and updated your design rationale. Well done!

---

#### Total (62/80)

#### hw4a points Back: 54 * 0.75 = 40.5

Late days used: 1 (2 left)

---

#### Additional Notes

Graded by: Diego Martinez (diegom@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/tyanneki/blob/master/grades/hw4c.md
