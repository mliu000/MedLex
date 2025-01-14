package model;

import java.util.*;

import java.io.*;
import java.nio.file.*;

/*
 * @Mu Ye Liu - Jan 2025
 * 
 * Represents the symptoms class. Uses a hashmap implementation of a dictionary to store the 
 * symptoms and its corresponding list of common words, as well as its definition, that can easily.
 * Also returns list of symptoms given common words.
 * 
 * Also contains methods that take a string input and returns the list of potential symptoms, 
 * sorted by relevance and likelihood
 * 
 * Uses the singleton method because we only need (and want) a single instance of this symptoms class
 * We don't want multiple instances because it could cause the .txt files to be different across 
 * different instances, which could potentially produce different unwanted results if one of the 
 * instance's hashmaps were modified. 
 */
public class Symptoms {

    /*
     * symp_words: Hashmap stores the symptoms as the keys, and its corrsponding list of common 
     * words as the value
     * symp_key: Hashmap stores the symptoms as the keys, and its definition as the values
    */
    private HashMap<String, List<String>> sympWords;
    private HashMap<String, String> sympDef;

    // Instance of the Symptoms class
    private static Symptoms instance;

    // Private constructor that only creates new instance if it doesn't exist yet
    private Symptoms() {
        this.sympWords = new HashMap<>();
        this.sympDef = new HashMap<>();
        loadCommonWords("backend/Symptoms_common_words.txt");
        loadDefinitions("backend/Symptoms_definitions.txt");
    }

    ///// CALLED BY THE FRONTEND /////
    
    /*
     * Given an single string input of words, return the top 4 symptoms, along with their respective
     * definitions in a list of string pairs
     * 
     * RETURNS: the list of top 4 symptoms along with their size, or null if the wordSet or 
     * symptomCount is empty (bad input);
     */
    public List<Pair<String, String>> getTop4Symptoms(String input) {
        // From the string input, get the wordset of distinct words
        Set<String> wordSet = inputToWordSet(input);
        // Return null if the wordSet is empty
        if (wordSet.isEmpty()) {
            return null;
        }

        // Stores the symptoms and its respective count
        List<Pair<String, Integer>> symptomCount = new ArrayList<>();
        getSymptomCounts(symptomCount, wordSet); 

        // If symptom count is empty, return null
        if (symptomCount.isEmpty()) {
            return null;
        }

        // If symptomCount is not empty, sort it, then get the top 3 symptoms, then the definitions
        sort(symptomCount);

        for (Pair<String, Integer> s: symptomCount) {
            System.out.println(s.getFirst() + ": " + s.getSecond());
        }
        return getTopSympDefs(symptomCount);
    }

    ///// HELPER METHODS /////
    
    /*
     * Get the definitions of the top symptoms. Returns List<String1, String2>, where String1 =
     * name of symptom, String2 = its corresponding definition
     * 
     * REQUIRES: symptomCount must be sorted
    */
    public List<Pair<String, String>> getTopSympDefs(List<Pair<String, Integer>> symptomCount) {
        // Set the number of entries to iterate through (= 4 or < 4 if the symptomCount size is less)
        int entries = Math.min(symptomCount.size(), 4);
        // Initialize the return list
        List<Pair<String, String>> topSympDefs = new ArrayList<>();
        for (int i = 0; i < entries; i++) {
            String symptom = symptomCount.get(i).getFirst();
            topSympDefs.add(new Pair<String,String>(symptom, sympDef.get(symptom)));
        }
        return topSympDefs;
    }
    
    // Sorts the list of pairs in desc. order based on the integer second value. O(nlog(n)) time
    private void sort(List<Pair<String, Integer>> symptomCount) {
        Collections.sort(symptomCount, new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> p1, Pair<String, Integer> p2) {
                return p2.getSecond().compareTo(p1.getSecond());
            }
        });
    }
    
    /*
     * Given a string, filter it so it does not contain special chars (eg. #, $) and numbers, 
     * and sets all letters to lower case, and trims the ends
     * 
     * Then, stores the words of the filtered string into set of string, and returns it. 
     * A set is used so there are no duplicate words (don't want that)
     * 
    */
    private Set<String> inputToWordSet(String input) {
        // Filters the string
        input = input.trim().replaceAll("[^a-zA-Z\\s]", "").toLowerCase(); 

        // Splits the string into array, immediately puts it into the set
        Set<String> wordSet = new HashSet<>();
        for (String word: input.split("\\s+")) {
            wordSet.add(word);
        }

        return wordSet;
    }

    /*
     * Fill the list that pairs the symptom name with occurrence count. If m is the size of the 
     * wordSet, and n is the size of the number of symptoms, then the runtime is O(mn);
     */
    private void getSymptomCounts(List<Pair<String, Integer>> symptomCount, Set<String> wordSet) {
        // Iterate through all the symptoms first
        for (Map.Entry<String, List<String>> entry: sympWords.entrySet()) {
            // Get the key and values. Change value to Hashset for O(1) runtime instead of O(n)
            String symptom = entry.getKey();
            Set<String> commonWords = new HashSet<>(entry.getValue()); 

            // Iterate over input wordset
            int count = 0;
            for (String word : wordSet) { // O(m)
                if (commonWords.contains(word)) { // O(1)
                    // If the set of common words contains the key, word in input set, inc count.
                    count++;
                }
            } 

            // If the count is greater than 0, put it into the hashset
            if (count > 0) {
                symptomCount.add(new Pair<String, Integer>(symptom, count));
            }
        }
    }

    ///// INITIALIZER METHODS ///// 
    
    // Loads the .txt file with the symptoms and common words into the corresonding hashmap
    private void loadCommonWords(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Split the line into the symptom and the list of common words
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String symptom = parts[0].trim();
                    String[] commonWords = parts[1].trim().split("\\s+");
                    // Add symptom and common words to the map
                    sympWords.put(symptom, Arrays.asList(commonWords));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Loads the .txt file with the symptoms and the definitions into the corresponding hashmap
    private void loadDefinitions(String filePath) {
        Path path = Paths.get(filePath);
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Split the line into the symptom and the list of common words
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String symptom = parts[0].trim();
                    String definition = parts[1].trim();

                    // Add symptom and common words to the map
                    sympDef.put(symptom, definition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 

    ///// GETTER METHODS ///// 

    // Gets the instance of the symptoms class. 
    public static Symptoms getInstance() {
        if (instance == null) {
            instance = new Symptoms();
        }
        return instance;
    }

    public HashMap<String, List<String>> getSympWords() { return sympWords; }
    public HashMap<String, String> getSympDef() { return sympDef; }
    
}
