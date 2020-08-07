package edu.cmu.cs.cs214.hw6;

import org.eclipse.jgit.lib.Repository;

import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class SimilarityEngineTests {
    private Repository rep;
    private Repository rep2;
    private SimilarityEngine engineSeq;
    private SimilarityEngine enginePar;

    @Before
    public void setup() {
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
        try {
            rep = repositoryBuilder.setGitDir(new File("../../.git"))
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .setMustExist(true)
                    .build();
            rep2 = repositoryBuilder.setGitDir(new File("../../.git"))
                    .readEnvironment() // scan environment GIT_* variables
                    .findGitDir() // scan up the file system tree
                    .setMustExist(true)
                    .build();

            engineSeq = new SimilarityEngine(rep, rep2, false);
            enginePar = new SimilarityEngine(rep, rep2, true);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testGetAllRevisions() {
        ArrayList<Revision> revs = SimilarityEngine.getAllRevisions(rep);
        ArrayList<Revision> revs2 = SimilarityEngine.getAllRevisions(rep2);
        assertEquals(revs.size(), revs2.size());
        assertEquals(revs.get(0).getFeatureVector(), revs2.get(0).getFeatureVector());
    }

    @Test
    public void testCalculateRevisionPairsParallel() {
        ArrayList<Revision> revs = SimilarityEngine.getAllRevisions(rep);
        ArrayList<Revision> revs2 = SimilarityEngine.getAllRevisions(rep2);
        ArrayList<RevisionPair> revPairs = SimilarityEngine.calculateRevisionPairsParallel(revs, revs2);
        assertEquals(revPairs.size(), revs.size() * revs2.size());
    }

    @Test
    public void testCalculateRevisionPairsSequential() {
        ArrayList<Revision> revs = SimilarityEngine.getAllRevisions(rep);
        ArrayList<Revision> revs2 = SimilarityEngine.getAllRevisions(rep2);
        ArrayList<RevisionPair> revPairs = SimilarityEngine.calculateRevisionPairsSequential(revs, revs2);
        assertEquals(revPairs.size(), revs.size() * revs2.size());
    }

    @Test
    public void testGetNMostSimilar() {
        assertEquals(engineSeq.getNMostSimilar(5).size(), 5);
        assertEquals(enginePar.getNMostSimilar(5).size(), 5);
        assertEquals(engineSeq.getNMostSimilar(4).get(0).getSimilarity(), 1.0, 0.0001);
        assertEquals(enginePar.getNMostSimilar(4).get(0).getSimilarity(), 1.0, 0.0001);
    }

}
