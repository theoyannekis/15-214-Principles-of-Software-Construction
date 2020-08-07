package edu.cmu.cs.cs214.hw1;

import java.util.HashMap;
import java.io.IOException;


/**
 * Takes a list of URLs on the command line and prints each URL with its pair whose web pages have the highest
 * cosine similarity. Prints a stack trace if any of the URLs are invalid, or if an exception occurs while reading
 * data from the URLs.
 */
public class FindClosestMatches {
    /**
     *
     * @param args urls of pages given as strings
     * @throws IOException print stack trace if exception occurs
     */
    public static void main(String[] args) throws IOException {
        int numDocs = args.length;
        // First build a map with keys: firstUrl + secondUrl and values: cosine similarity
        HashMap<String, Double> similarityMap = new HashMap<>();
        for (int i = 0; i < numDocs; i++) {
            Document d = new Document(args[i]);
            for (int j = i + 1; j < numDocs; j++) {
                double similarity = d.cosineSimilarity(args[j]);
                similarityMap.put(args[i] + " " + args[j], similarity);
            }
        }
        // Loop through each url and print it and the url with which it has the highest cosine similarity
        for (String url : args) {
            double maxSimilarity = 0;
            String maxUrl = "";
            for (String pair : similarityMap.keySet()) {
                String[] splitStrings = pair.split("\\s+");
                String url1 = splitStrings[0];
                String url2 = splitStrings[1];
                double similarity = similarityMap.get(pair);
                // if the current url is the first url in the key pair
                if (url1.equals(url)) {
                    if (maxSimilarity <= similarity) {
                        maxSimilarity = similarity;
                        maxUrl = url2;
                    }
                }
                else if (url2.equals(url)) {
                    if (maxSimilarity <= similarity) {
                        maxSimilarity = similarity;
                        maxUrl = url1;
                    }
                }
            }
            System.out.println(url + " " + maxUrl);
        }
    }
}