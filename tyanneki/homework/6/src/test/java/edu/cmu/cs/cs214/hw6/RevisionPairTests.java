package edu.cmu.cs.cs214.hw6;

import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Collection;

import static org.junit.Assert.*;

public class RevisionPairTests {
    private Repository rep;
    private Repository rep2;
    private Revision revision;
    private Revision revision2;
    private RevisionPair revisionPair;
    private RevisionPair revisionPair2;

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

            Collection<Ref> allRefs = rep.getAllRefs().values();

            RevWalk revWalk = new RevWalk(rep);
            for (Ref ref : allRefs) {
                revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
            }

            RevCommit commitA = revWalk.next();
            RevCommit commitB = revWalk.next();

            revision = new Revision(rep, commitA, commitB);

            allRefs = rep2.getAllRefs().values();

            revWalk = new RevWalk(rep2);
            for (Ref ref : allRefs) {
                revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
            }

            RevCommit commit2A = revWalk.next();
            RevCommit commit2B = revWalk.next();

            revision2 = new Revision(rep2, commit2A, commit2B);

            revisionPair = new RevisionPair(revision, revision2);

            commit2A = revWalk.next();
            commit2B = revWalk.next();
            revisionPair2 = new RevisionPair(revision, new Revision(rep2, commit2A, commit2B));


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testCalculateSimilarity() {
        assertEquals(revisionPair.getSimilarity(), 1.0, 0.0001);
        assertNotEquals(revisionPair2.getSimilarity(), 1.0, 0.0001);

        RevisionPair r = new RevisionPair(revision, new Revision(rep, null, null));
        assertEquals(r.getSimilarity(), 0.0, 0.0001);
    }

    @Test
    public void testGetSimilarity() {
        assertEquals(revisionPair.getSimilarity(), revisionPair.calculateSimilarity(), 0.0001);
        assertEquals(revisionPair2.getSimilarity(), revisionPair2.calculateSimilarity(), 0.0001);
    }
}
