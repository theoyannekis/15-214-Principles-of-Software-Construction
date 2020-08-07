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

public class RevisionTests {
    private Repository rep;
    private Repository rep2;
    private Revision revision;
    private Revision revision2;


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


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testMakeFeatureVector() {
        assertEquals(revision.getFeatureVector().size(), revision.makeFeatureVector().size());
        assertEquals(revision.getFeatureVector().get(0), revision.makeFeatureVector().get(0));
        assertEquals(revision.getFeatureVector().get(1), revision.makeFeatureVector().get(1));
        assertEquals(revision.getFeatureVector().get(2), revision.makeFeatureVector().get(2));
        assertEquals(revision.getFeatureVector().get(3), revision.makeFeatureVector().get(3));
        assertEquals(revision.getFeatureVector(), revision2.getFeatureVector());

    }

}
