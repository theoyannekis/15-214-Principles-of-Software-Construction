package edu.cmu.cs.cs214.hw1;

import java.io.IOException;


/**
 * Takes a list of URLs on the command line and prints the two URL whose web pages have the highest cosine similarity.
 * Prints a stack trace if any of the URLs are invalid, or if an exception occurs while reading data from
 * the URLs.
 */
public class FindClosestMatch {
    /**
     *
     * @param args urls of pages given as strings
     * @throws IOException print stack trace if exception occurs
     */
    public static void main(String[] args) throws IOException {
        int numDocs = args.length;
        double maxSimilarity = 0;
        String firstUrl = "";
        String secondUrl = "";
        for (int i = 0; i < numDocs; i++) {
            Document d = new Document(args[i]);
            for (int j = i + 1; j < numDocs; j++) {
                double similarity = d.cosineSimilarity(args[j]);
                if (similarity >= maxSimilarity) {
                    maxSimilarity = similarity;
                    firstUrl = args[i];
                    secondUrl = args[j];
                }
            }
        }
        System.out.println(firstUrl + " " + secondUrl);
    }
}