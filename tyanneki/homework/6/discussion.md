Strategy:

My first attempt at parallelism for this program was to use parallel streams instead of loops. My implementation creates
a list of Revisions for each repository and then creates a RevisionPair for each combination of revisions between
the two repositories. This is an expensive operation. For example if a repository has had 200 revisions in its lifetime,
there will be 40,000 RevisionPairs created. This adds up quickly. I wanted to parallelize this process instead of having
the nested for loop as I do in the serializable implementation. Its a lot of operations that do not depend on each other in
any way so it would be safe to make parallel. I did this but did not see improvements in performance. My guess as to why
this happened is because of the overhead of Streams. In my case there were not an absurd amount of operations to do, around 40,000,
and it appears that the overhead of the Streams did not outweigh the gains made by running it in parallel. 

The second attempt was to use the java.util.concurrent library, specifically the ExecuterService class in order to 
run some tasks in parallel using multiple threads. The work I did in parallel was the actual retrieval of the Revisions from
the repositories. For every child-parent commit pairing a Revision object had to be created. This occurred for both repositories 
so a unit of work in this case is the making of a Revision object. I experimented with different numbers of threads
as shown below. I did not have to explicitly protect any data in the face of concurrency because 
the operations I was doing were thread-safe. All the threads were doing is make objects and put them in an List. The order
that they are inserted into the List does not matter and the actual creation of the objects is thread-safe. Therefore I didn't need
to explicitly make any function synchronous or anything.

Benchmarking:

I did all benchmarking tests with my andrew repository against itself and finding the 10 most similar. N in this case is
irrelevant towards the execution time because that is only relevant at the very end when we take a sublist of an arraylist.
I chose my andrew repository because it is the repository with the most commits that I have access to. The advantages of 
parallelism become more clear the larger the inputs so I wanted to use as large of a repository as possible. 

I first tested the nested for loop way of creating RevisionPairs vs parallelStreams. I tested by finding the 10 most similar
between my andrew repository. I averaged the results of 5 executions each when my repository had 141 commits. The sequential vs. stream tests
were done with 135 commits.

Sequential: 2.703 sec

parallel streams: 2.737 sec

This is effectively the same. There is no significant difference.

Next I implemented the parallelism using ExecuterService with varying numbers of threads. The results are below.

The execution always took a second or two longer the first time after I built the project, no matter if parallel or sequential.

Sequential RevisionPairs 1 thread: 2.686 sec

Sequential RevisionPairs 2 threads: 1.821 sec

Sequential RevisionPairs 3 threads: 1.880 sec

Sequential RevisionPairs 4 threads: 1.864 sec

Sequential RevisionPairs 5 threads: 1.739 sec

Sequential RevisionPairs 6 threads: 1.840 sec

Sequential RevisionPairs 7 threads: 1.801 sec

Sequential RevisionPairs 20 threads: 1.828 sec

Parallel RevisionPairs 1 thread: 2.687 sec

Parallel RevisionPairs 2 threads: 2.098 sec

Parallel RevisionPairs 3 threads: 1.796 sec

Parallel RevisionPairs 4 threads: 1.846 sec

Parallel RevisionPairs 5 threads: 1.881 sec

Parallel RevisionPairs 6 threads: 1.758 sec

Parallel RevisionPairs 7 threads: 1.787 sec

Parallel RevisionPairs 20 threads: 1.718 sec

These tests were interesting because we can see about a 35% decrease in execution time after implementing the multi-threaded approach.
There was a big initial decrease when we increase to 2 threads as opposed to 1, and then from there there is not much improvement.
This testing was done on a 2 core machine, which helps to explain why there was such a huge increase when we added a thread. 
Since there's only 2 cores it makes sense that there is not much decrease in execution time past the addition of that second
thread. This benchmarking most influenced this program by displaying to me that just using parallel streams was not effective 
in decreasing execution time. I had to add the multi-threading and I felt that adding that in was a big success and I knew
that because of the second set of benchmarking tests I did. Muti-threading did not provide a linear decrease in execution
time because there is only so much 2 cores can do no matter how many threads there are. There is also so overhead required
in creating and scheduling a large number of threads that starts to balance out their benefits.