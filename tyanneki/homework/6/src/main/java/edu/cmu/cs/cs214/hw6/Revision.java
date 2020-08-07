package edu.cmu.cs.cs214.hw6;

import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent the changes that occur between and child and parent commit
 */
public class Revision {
    private RevCommit parent;
    private RevCommit child;
    private List<DiffEntry> diffEntry;
    private Repository repository;
    private ArrayList<Double> featureVector;


    Revision(Repository repos, RevCommit p, RevCommit c) {
        parent = p;
        child = c;
        repository = repos;
        //If we're comparing a commit to nothing
        if (p == null || c == null) {
            diffEntry = new ArrayList<>();
        } else {
            FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
            DiffFormatter diffFormatter = new DiffFormatter(stdout);
            diffFormatter.setRepository(repository);
            try {
                diffEntry = diffFormatter.scan(child, parent);
                featureVector = makeFeatureVector();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    /**
     * Uses properties of the parent and child commits to create a list representing fetures to be used in similarity.
     * @return A List of the scores of this Revision in linesDeleted, linesAdded, score, and filesChanged
     */
    ArrayList<Double> makeFeatureVector() {
        ArrayList<Double> featureVec = new ArrayList<>();
        try {
            FileOutputStream stdout = new FileOutputStream(FileDescriptor.out);
            DiffFormatter diffFormatter = new DiffFormatter(stdout);
            diffFormatter.setRepository(repository);
            int filesChanged = diffEntry.size();
            int linesDeleted = 0;
            int linesAdded = 0;
            int score = 0;
            for (DiffEntry entry : diffEntry) {
                //https://stackoverflow.com/questions/19467305/using-the-jgit-how-can-i-retrieve-the-line-numbers-of-added-deleted-lines
                for (Edit edit : diffFormatter.toFileHeader(entry).toEditList()) {
                    linesDeleted += edit.getEndA() - edit.getBeginA();
                    linesAdded += edit.getEndB() - edit.getBeginB();
                    score += entry.getScore();
                }
            }
            featureVec.add(Math.sqrt(score));
            featureVec.add(Math.sqrt(2.2 * filesChanged));
            featureVec.add(Math.sqrt(1.3 * linesAdded));
            featureVec.add(Math.sqrt(1.7 * linesDeleted));
            return featureVec;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    ArrayList<Double> getFeatureVector() {
        return featureVector;
    }

    @Override
    public String toString() {
        return "Child: " + child.getName() + " , " + "Parent: " + parent.getName();
    }
}
