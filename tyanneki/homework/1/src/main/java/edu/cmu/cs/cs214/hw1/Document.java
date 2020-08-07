package edu.cmu.cs.cs214.hw1;

import java.io.IOException;
import java.util.Scanner;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * Represents a web page identified by its url. Contains functionality to calculate the cosine similarity between
 * two web pages, a constructor, and a toString method for the url of the document.
 */
public class Document {

    private String url;

    Document(String url){
        this.url = url;
    }

    /**
     * Calculates cosine similarity between document and another web page accessed through the urlString parameter.
     *
     * @param urlString the url as a string of the website to compute the cosine similarity with
     * @return the cosine similarity of the two pages. 0 <= similarity <= 1
     */
    public double cosineSimilarity(String urlString) {
        double similarity = 0;
        HashMap<String, Integer> string1Map = new HashMap<>();
        HashMap<String, Integer> string2Map = new HashMap<>();
        try {
            //Make the hash map for the Document
            Scanner sc1 = new Scanner(new URL(this.url).openStream());
            while(sc1.hasNext()) {
                String word = sc1.next();
                // Check if word is already in our table. If so increment counter.
                if (string1Map.containsKey(word)) {
                    int wordCount = string1Map.get(word);
                    string1Map.put(word, wordCount + 1);
                }
                else {
                    string1Map.put(word, 1);
                }
            }
            //Make the hash map for the page passed in as a parameter to this method
            Scanner sc2 = new Scanner(new URL(urlString).openStream());
            while(sc2.hasNext()) {
                String key = sc2.next();
                // Check if key is already in our table. If so increment counter.
                if (string2Map.containsKey(key)) {
                    int wordCount = string2Map.get(key);
                    string2Map.put(key, wordCount + 1);
                }
                else {
                    string2Map.put(key, 1);
                }
            }

            // Create the intersection of the two sets of keys
            Set<String> intersection = string1Map.keySet();
            Set<String> string2Set = string2Map.keySet();
            intersection.retainAll(string2Set);

            double numerator = 0;
            double denominator1 = 0;
            double denominator2 = 0;
            for (String key: intersection) {
                double count1 = string1Map.get(key);
                double count2 = string2Map.get(key);
                numerator += count1 * count2;
                denominator1 += Math.pow(count1, 2);
                denominator2 += Math.pow(count2, 2);
            }
            denominator1 = Math.sqrt(denominator1);
            denominator2 = Math.sqrt(denominator2);

            if (denominator1 * denominator2 != 0) {
                similarity = numerator / (denominator1 * denominator2);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return similarity;
    }

    /**
     * Gives the url of the document as a string
     *
     * @return the url of the document
     */
    public String toString(){
        return this.url;
    }

}
