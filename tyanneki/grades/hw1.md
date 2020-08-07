hw1 Feedback
============

#### Successful use of Git, GitHub, and build automation tools (4/5)

* -1, Your commit history ([link](https://github.com/CMU-17-214/tyanneki/commits/master)) is not useful. A useful commit history separates work into multiple commits, instead of one or two for the whole assignment. Each commit should have a concise, but descriptive name about what was changed. Committing your work incrementally protects you from data loss or network problems, which might otherwise cause you to lose work or fail to meet the homework deadlines (which are strictly enforced). For more information on writing useful commit messages, see [here](https://git-scm.com/book/ch5-2.html#Commit-Guidelines) or [here](http://chris.beams.io/posts/git-commit/)

#### Basic proficiency in Java (18/20)

* -1, You should declare variables by their interfaces, not by the class that implements that interface. For example, it is better practice to declare a variable as having type `Map` instead of `HashMap`. ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L29)) See Effective Java Item 64.

* -1, Instead of doing time consuming operations such as repeatedly performing a web request to the same URL, you should perform these only once upon Document initialization and cache the result for faster method calls later on ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L33-L57)).

* -0.1, Instead of doing time consuming operations such as calculating constant values, you can perform these only once upon initialization and store them for faster method calls later on. In particular, the denominator can be calculated immediately for an instance of `Document`; then, you can reuse this value for future computations ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L65-L75)).

* -0.1, Please add `@Override` annotation before overriding methods. ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L92)) This annotation helps because (1) it adds a compile-time check that makes sure you're actually overriding a method, and (2) it makes your code easier to understand to the human reader.

* -0.1, You should auto-close resources with a `try-with-resources` block or manually close with a `final` block when you are done with them ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L33-L34)). Although it does not matter in a short lived command line program you wrote here, for larger-scale programs, it is important to ensure that leaky resources are closed. Example:
```
try (Scanner sc = new Scanner(new URL(this.url).openStream())) {
  while (sc.hasNext()) {
    ...
  }
}
```

* -0.1, It's a good idea to declare instance variables as `final` if their values are set only once: in the constructor.  We will see later in this course that immutability (i.e. data that does not change) is a powerful concept that can greatly simplify design. ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L15)) (Note that variables having reference types, like Map and List, can be given the `final` modifier even if the data they contain is changed. `final`, in this case, means that the _reference_ doesn't change.)

* -0.1, Catching an exception and explicitly printing the stack trace with printStackTrace() is unnecessary. Either catch the exception and only print an error message, or let the exception propagate outward. If an exception is thrown and not handled, the program will terminate and automatically print a stack trace ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L81-L83)).

#### Fulfilling the technical requirements of the program specification (12/15)

* -3, The Document class does not conform to the specification ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L27)). `cosineSimilarity` should take a Document instead of a String.

#### Documentation and code style (9/10)

* -1, You have large bodies of copy-and-pasted code in your project. This makes your code hard to read and harder to modify. ([link](https://github.com/CMU-17-214/tyanneki/blob/b810d1e083db082dabeb753e0ba8f641c432a238/homework/1/src/main/java/edu/cmu/cs/cs214/hw1/Document.java#L34-L57))


---

#### Total (43/50)

Late days used: 0 (5 left)

---

#### Additional Notes

Graded by: Megan Yu (mtyu@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-17-214/tyanneki/blob/master/grades/hw1.md
