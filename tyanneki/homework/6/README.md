The program contains two main methods which can execute the program. These exist in SequentialMain.java and ParallelMain.java. To run the program you must supply 3 command line arguments, 'path to git repository A' 'path to git repository B' 'N'. This is the relative path to the git repository. The goal of the program is to list the N most similar revisions between the two repositories and the N argument is that same N. 

As an example, to run this program locally to find the 10 most similar revisions between my andrew git repository and itself, do:

For Sequential: run SequentialMain.main() with args "../../.git" "../../.git" "10"

For Parallel: run ParallelMain.main() with args "../../.git" "../../.git" "10"

The most related revisions will be printed on the console.