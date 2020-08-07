hw5b Feedback
============

#### Core Framework Implementation (61/70)
* Technical requirements (30/30), Your framework meets the technical requirements to a reasonable degree. Good job!
* Framework functionality (12/12), Your framework works reasonably well. Good job!
* Unit Testing (4/8), Your tests seem mostly reasonable, but we identified the following minor issues: 
  * Your unit tests for your framework do not use testing stubs. To test your framework you should write a simple stub data plugin class (i.e., a mock data plugin) and a simple stub display plugin class (i.e., a mock display plugin) to generate and consume sample data so that you can test the behavior of your framework. Because you are writing both stub classes for testing purposes, your stub data plugin and stub display plugin can be coupled to each other so that you can test the control flow for the framework as it processes a specific set of data.
* General design (15/20): The general design of your framework is reasonable, but we identified some minor issues.
 * Your display plugin should not directly display a GUI. It should return a JPanel/JFrame (or for your framework, perhaps a Plot) to the framework. This allows the framework to have some control over what output is being displayed to the user.
 * Your non-qualified package edu.cmu.cs.cs214.hw5.core should have a properly qualified name (e.g., edu.cmu.cs.cs214.hw5.framework.plugin). The current non-qualified name is violating the standard Java conventions for package names, and is incompatible with the wide world of Java code (for which package names are required to be globally unique).


#### Sample Plugins (25/30): Your provided sample plugins are adequate and meet our expectations. Good job!
* -5, Some of your data or display plugins are structurally almost identical (CVS and JSON loading). The goal of writing multiple plugins was to highlight the variety of plugins that your framework supports.

#### High quality documentation and style (25/25)
* Documentation (15/15): Your documentation meets our expectations. Good job!
* Project Configuration and Gradle (5/5): Build automation using gradle and Travis CI seems to work fine and we were able to start your framework using `gradle run`. Nice! 
* Style (5/5): Your implementation meets our expectations regarding style.


---

#### Total (111/125)

Late days used: 2 (0 left for hw5)

---

#### Additional Notes

Graded by: Echo Wang (echow1@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/tyanneki/blob/master/grades/hw5b.md
