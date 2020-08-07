package edu.cmu.cs.cs214.hw6;

import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import java.io.File;
import java.util.*;

/**
 * Class to pars command line arguments given to either the parallel or sequential main functions
 */
class ArgParser {

    private static int NUMARGS = 3;

    /**
     * parses command line arguments passed into program and throws appropriate errors if invalid input given
     * @param args the command line arguments passed into function
     * @param parallelFlag true if command line arguments the parallel implementation is desired. false if sequential
     */
    static void parseArgs(String[] args, boolean parallelFlag) {
        try {
            //long startTime = System.currentTimeMillis();

            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
            if (args.length < NUMARGS) {
                throw new IllegalArgumentException("Please provide 3 arguments in form path1 path2 N parallelFlag");
            }
            File repA;
            File repB;
            try {
                repA = new File(args[0]);
                repB = new File(args[1]);
            } catch (Exception e) {
                throw new IllegalArgumentException("Please provide valid paths to a git repositories");
            }
            Repository repositoryA = repositoryBuilder.setGitDir(repA)
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .setMustExist(true)
                    .build();
            Repository repositoryB = repositoryBuilder.setGitDir(repB)
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .setMustExist(true)
                    .build();

            int N;

            try {
                N = Integer.parseInt(args[2]);
            } catch (Exception e) {
                throw new IllegalArgumentException("Please provide a valid integer as third argument");
            }

            SimilarityEngine engine = new SimilarityEngine(repositoryA, repositoryB, parallelFlag);

            //Get the pairs. They are sorted in descending order of similarity score
            ArrayList<RevisionPair> pairs = engine.getNMostSimilar(N);

            for (RevisionPair pair : pairs) {
                System.out.println(pair);
            }

//            long endTime = System.currentTimeMillis();
//
//            if (parallelFlag) {
//                System.out.println("That took " + (endTime - startTime) + " milliseconds in Parallel");
//            } else {
//                System.out.println("That took " + (endTime - startTime) + " milliseconds Sequentially");
//
//            }

        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        System.out.println("Finished Program");
    }

}
