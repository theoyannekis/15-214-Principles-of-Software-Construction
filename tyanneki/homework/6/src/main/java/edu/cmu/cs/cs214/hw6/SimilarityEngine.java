package edu.cmu.cs.cs214.hw6;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

class SimilarityEngine {
    private Repository repA;
    private Repository repB;
    private ArrayList<RevisionPair> revisionPairs;

    SimilarityEngine(Repository rA, Repository rB, boolean pFlag) {
        repA = rA;
        repB = rB;

        ArrayList<Revision> revisionsA;
        ArrayList<Revision> revisionsB;
        if (!pFlag) {
            revisionsA = getAllRevisions(repA);
            revisionsB = getAllRevisions(repB);
            revisionPairs = calculateRevisionPairsParallel(revisionsA, revisionsB);
            revisionPairs.sort(RevisionPair.SimilarityComparator);
            Collections.reverse(revisionPairs);
        } else {

            //The Executer Service code is adapted from a thread of Stackoverflow that I can't find again
            ExecutorService Exec = Executors.newFixedThreadPool(20);
            List<Callable<ArrayList<Revision>>> tasks = new ArrayList<>();
            //The two tasks added to the execService are the creation of the Revisions for each repository
            tasks.add(() -> getAllRevisions(repA));
            tasks.add(() -> getAllRevisions(repB));
            List<Future<ArrayList<Revision>>> results;
            try {
                results = Exec.invokeAll(tasks);
                revisionsA = results.get(0).get();
                revisionsB = results.get(1).get();
                revisionPairs = calculateRevisionPairsSequential(revisionsA, revisionsB);
                revisionPairs.sort(RevisionPair.SimilarityComparator);
                Collections.reverse(revisionPairs);
                Exec.shutdown();

            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    //Modified From:
    //https://github.com/centic9/jgit-cookbook/blob/master/src/main/java/org/dstadler/jgit/api/WalkAllCommits.java
    /**
     * Creates a Revision object for all parent-child commit pairs in a given repository
     * @param repository the repository that we wish to get all the Revisions of
     * @return A List of all Revisions in the given repository
     */
    static ArrayList<Revision> getAllRevisions(Repository repository) {
        ArrayList<Revision> revisions = new ArrayList<>();

        try {
            Collection<Ref> allRefs = repository.getAllRefs().values();

            RevWalk revWalk = new RevWalk(repository);
            for (Ref ref : allRefs) {
                revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
            }
            for (RevCommit commit : revWalk) {
                try {
                    RevCommit parent = commit.getParent(0);
                    revisions.add(new Revision(repository, parent, commit));
                } catch (ArrayIndexOutOfBoundsException e) {
                    //When there is not parent since we're at the tail commit
                    revisions.add(new Revision(repository, null, commit));
                }

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return revisions;
    }

    /**
     * Make RevisionPair objects for every single combination of Revisions between the two repositories in parallel
     * @param revsA Revisions from repositoryA
     * @param revsB Revisions from repositoryB
     * @return List of all RevisionPairs made between the two repository's Revisions
     */
    static ArrayList<RevisionPair> calculateRevisionPairsParallel(ArrayList<Revision> revsA, ArrayList<Revision> revsB) {

        return revsA.parallelStream()
                .flatMap(revA -> revsB.parallelStream()
                        .map(revB -> new RevisionPair(revA, revB))
                ).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Make RevisionPair objects for every single combination of Revisions between the two repositories in sequence
     * @param revsA Revisions from repositoryA
     * @param revsB Revisions from repositoryB
     * @return List of all RevisionPairs made between the two repository's Revisions
     */
    static ArrayList<RevisionPair> calculateRevisionPairsSequential(ArrayList<Revision> revsA, ArrayList<Revision> revsB) {
        ArrayList<RevisionPair> pairList = new ArrayList<>();


        for (Revision revA : revsA) {
            for (Revision revB : revsB) {
                RevisionPair pair = new RevisionPair(revA, revB);
                pairList.add(pair);
            }
        }
        return pairList;
    }

    /**
     * Using the sorted list of RevisionPairs, gives the N most similar
     * @param n the number of RevisionPairs that we would like to get
     * @return A List of the n most similar RevisionPairs in the two repositories
     */
    ArrayList<RevisionPair> getNMostSimilar(int n) {

        return new ArrayList<>(revisionPairs.subList(0, n));
    }

}

