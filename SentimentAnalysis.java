import java.io.InputStream;
import java.util.*;

public class SentimentAnalysis {
    public static void main(String[] args)
    {
        final boolean PRINT_TREES = false;  // whether or not to print extra info about the maps.

        BSTMap<String, Integer> wordFreqs = new BSTMap<String, Integer>();
        BSTMap<String, Integer> wordTotalScores = new BSTMap<String, Integer>();
        Set<String> stopwords = new TreeSet<String>();

        System.out.print("Enter filename: ");
        Scanner scan = new Scanner(System.in);
        String filename = scan.nextLine();

        processFile(filename, wordFreqs, wordTotalScores);

        System.out.println("Number of words is: " + wordFreqs.size());
        System.out.println("Height of the tree is: " + wordFreqs.height());

        if (PRINT_TREES)
        {
            System.out.println("Preorder:  " + wordFreqs.preorderKeys());
            System.out.println("Inorder:   " + wordFreqs.inorderKeys());
            System.out.println("Postorder: " + wordFreqs.postorderKeys());
            printFreqsAndScores(wordFreqs, wordTotalScores);
        }

        removeStopWords(wordFreqs, wordTotalScores, stopwords);

        System.out.println("After removing stopwords:");
        System.out.println("Number of words is: " + wordFreqs.size());
        System.out.println("Height of the tree is: " + wordFreqs.height());

        if (PRINT_TREES)
        {
            System.out.println("Preorder:  " + wordFreqs.preorderKeys());
            System.out.println("Inorder:   " + wordFreqs.inorderKeys());
            System.out.println("Postorder: " + wordFreqs.postorderKeys());
            printFreqsAndScores(wordFreqs, wordTotalScores);
        }

        while (true)
        {
            System.out.print("\nEnter a new review to analyze: ");
            String line = scan.nextLine();
            if (line.equals("quit")) break;

            String[] words = line.split(" ");

            // The words[] array holds the new movie review to analyze.
            // Get the average sentiment for each word (skipping stop words),
            // then calculate the average of those sentiments.  This "average of averages"
            // becomes your overall sentiment analysis score.
            double overallAvgSent = 0;
            double count = 0;
            for (int i = 0; i < words.length; i++) {
                if (stopwords.contains(words[i])) {
                    System.out.println("Skipped - Stop word");
                }
                else if (wordFreqs.get(words[i]) == null) {
                    System.out.println("Skipped - New word");
                }
                else {
                    double avgSent = (double)wordTotalScores.get(words[i]) / wordFreqs.get(words[i]);
                    overallAvgSent += avgSent;
                    count += 1;
                    System.out.println("Average sentiment score for the word " + words[i] + " is: " + avgSent);
                }
            }
            System.out.println("Average sentiment score for the review is: " + overallAvgSent / count);
        }
    }

    /**
     * Read the file specified to add proper items to the word frequencies and word scores maps.
     */
    private static void processFile(String filename,
                                    BSTMap<String, Integer> wordFreqs, BSTMap<String, Integer> wordTotalScores)
    {
        InputStream is = SentimentAnalysis.class.getResourceAsStream(filename);
        if (is == null) {
            System.err.println("Bad filename: " + filename);
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();
            String[] words = line.split(" ");

            // You have an array of words.  The first word in the array is the score.
            // Process all the remaining words to add them to the wordFreqs and wordTotalScores maps in an appropriate way.
            for (int i = 1; i < words.length; i++) {
                // If true, it's in the tree
                if (wordFreqs.containsKey(words[i])) {
                    // Increase old frequency by 1
                    int oldFreq = wordFreqs.get(words[i]);
                    wordFreqs.put(words[i], oldFreq + 1);
                    // Increase the old rating
                    int wordScore = Integer.parseInt(words[0]);
                    int oldScore = wordTotalScores.get(words[i]);
                    wordTotalScores.put(words[i], oldScore + wordScore);
                }
                else {
                    // Add the frequency of the word to 1
                    wordFreqs.put(words[i], 1);
                    // Add this rating of the movie to the word
                    wordTotalScores.put(words[i], Integer.parseInt(words[0]));
                }
            }
        }
        scan.close();
    }

    /**
     * Print a table of the words found in the movie reviews, along with their frequencies and total scores.
     * Hint: Call wordFreqs.inorderKeys() to get a list of the words, and then loop over that list.
     */
    private static void printFreqsAndScores(BSTMap<String, Integer> wordFreqs, BSTMap<String, Integer> wordTotalScores)
    {
        // Printing the words as well as their frequencies and total scores
        List listOfWords = wordFreqs.inorderKeys();
        for (int i = 0; i < listOfWords.size(); i++) {
            System.out.print("Word: " + listOfWords.get(i) + " ");
            System.out.print("Frequency: " + wordFreqs.get(listOfWords.get(i).toString()) + " ");
            System.out.print("Total Score: " + wordTotalScores.get(listOfWords.get(i).toString()) + "\n");
        }
    }

    /**
     * Read the stopwords.txt file and add each word to the stopwords set.  Also remove each word from the
     * word frequencies and word scores maps.
     */
    private static void removeStopWords(BSTMap<String, Integer> wordFreqs,
                                        BSTMap<String, Integer> wordTotalScores, Set<String> stopwords)
    {
        InputStream is = SentimentAnalysis.class.getResourceAsStream("stopwords.txt");
        if (is == null) {
            System.err.println("Bad filename: " + "stopwords.txt");
            System.exit(1);
        }
        Scanner scan = new Scanner(is);

        while (scan.hasNextLine()) {
            String word = scan.nextLine();

            stopwords.add(word);
            wordFreqs.remove(word);
            wordTotalScores.remove(word);
        }
        scan.close();
    }
}
