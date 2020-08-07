hw5a Feedback
============

#### Description of Framework (25/25), Your framework description is complete, good job!

#### Framework Design (29/35)

Basic Structure (Understanding + Old Concepts) (15/15), You have demonstrated a good understanding of responsibility assignment and control flow. Well done!
* -0.1, Your framework should be able to manage multiple visualization plugins simultaneously. Your design model suggests that only one plugin can be supported at a time. 

Framework/Plugin Interaction & Control Flow (New Concepts) (9/15), You have demonstrated a good understanding of the assignment of responsibilities between a framework and its plugins, but we identified the following minor issues: 
* -3, The visualization plugins should not directly (or indirectly) initiate queries to the data sources. This should be done by the data plugin when the user selects it (and the framework initializes it).
* -1, Using the observer pattern would allow for improved communication between the framework and the plugins. Defining lifecycle methods in the plugin interface (such as `onRegister()` and `onClose()`) would allow for plugins to run initialization and teardown code as necessary.
* -2, It is unclear which user-initiated interaction involves the framework querying the data plugins for data. Some method in the API for the framework should allow the user to choose which plugin to load data from.

Style (5/5), Your notation meets our expectations regarding style.

#### Planning (10/10), Your project planning fully meets our expectations, good job!

#### Presentation (20/20)

Talk quality (17/17), The quality of your presentation fully met our expectations, good job!
Timing (3/3), Your presentation was an appropriate length.

#### Other Feedback
* We have no additional feedback for you, and look forward to seeing your completed framework!

---

#### Total (84/90)

Late days used: 0 (2 left for hw5)

---

#### Additional Notes

Graded by: Echo Wang (echow1@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/tyanneki/blob/master/grades/hw5a.md
