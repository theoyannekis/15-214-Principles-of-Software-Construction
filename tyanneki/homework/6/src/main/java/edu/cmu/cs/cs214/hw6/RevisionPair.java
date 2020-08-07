package edu.cmu.cs.cs214.hw6;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Class representing a pair of Revisions, one from each repository.
 */
class RevisionPair {
    private Revision revisionA;
    private Revision revisionB;
    private double similarity;

    RevisionPair(Revision revA, Revision revB) {
        revisionA = revA;
        revisionB = revB;
        similarity = calculateSimilarity();


    }

    /**
     * Comparator used to sort a list of RevisionPairs by their similarity
     */
    static Comparator<RevisionPair> SimilarityComparator = (a, b) -> {
        double simA = a.getSimilarity();
        double simB = b.getSimilarity();
        return Double.compare(simA, simB);
    };


    /**
     * Uses cosine similarity of feature vectors representing Revisions to calculate similarity between two
     * @return A similarity between the two Revisions making up the RevisionPair. 0.0-1.0 with 1.0 being identical
     */
    double calculateSimilarity() {
        ArrayList<Double> featuresA = revisionA.getFeatureVector();
        ArrayList<Double> featuresB = revisionB.getFeatureVector();
        if (featuresA == null || featuresB == null) {
            return 0.0;
        }
        double dotProd = 0.0;
        double magA = 0.0;
        double magB = 0.0;
        for (int i = 0; i < featuresA.size(); i++) {
            dotProd += featuresA.get(i) * featuresB.get(i);
            magA += Math.pow(featuresA.get(i), 2);
            magB += Math.pow(featuresB.get(i), 2);
        }
        double result =  dotProd / (Math.sqrt(magA) * Math.sqrt(magB));

        //Deal with some casting/rounding errors
        if (result > 1.0) {
            return 1.0;
        } else {
            return result;
        }
    }

    double getSimilarity() {
        return similarity;
    }

    public String toString() {
        return revisionA.toString() + " ; " + revisionB.toString() + " : " + Double.toString(similarity);
    }
}
