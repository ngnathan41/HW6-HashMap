import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.io.File;
import java.util.Set;
import java.util.HashSet;
/**The Passage word represents a text by title, similarity to other Passages, and word frequencies.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class Passage {
    private int wordCount;
    private String title;
    private HashMap<String, Double> similarTitles;
    private HashMap<String, Integer> wordFrequencies;
    private static HashSet<String> stopWords = new HashSet<>();

    /**Loads stopWords with words from a file.
     *
     * @param f File to extract stop words from.
     */
    public static void initiate(File f){
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                stopWords.add(line.trim().toLowerCase());
            }
            br.close();

        }
        catch (Exception ignored) {}
    }

    /**Initializes Passage with a given title and file.
     *
     * @param title Title of passage.
     * @param file File of passage.
     */
    public Passage(String title, File file){
        this.title = title;
        wordFrequencies = new HashMap<>();
        similarTitles = new HashMap<>();
        wordCount = 0;
        parseFile(file);
    }

    /**Parses file and counts words and frequency of words.
     *
     * @param file File to parse words from.
     */
    public void parseFile(File file){
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                for (String word : words) {
                    word = word.trim().replaceAll("[^a-z]", "");
                    wordCount++;
                    if (word.isEmpty() || stopWords.contains(word))
                        continue;
                    wordFrequencies.put(word,
                      wordFrequencies.getOrDefault(word, 0) + 1);
                }
            }
        }
        catch (Exception e) {
            System.out.println("Could not parse file:" + file.getName());
        }
    }

    /**Calculates the similarity of two passages based on their words and it's frequency.
     *
     * @param passage1 First passage to compares.
     * @param passage2 Second passage to compare.
     * @return Percentage of similarity of both passages from 0-1 inclusive.
     */
    public static double cosineSimilarity(Passage passage1, Passage passage2){
        double dotProduct, p1Mag, p2Mag;
        dotProduct = p1Mag = p2Mag = 0;
        HashMap<String, Integer> p1 = passage1.getWordFrequencies();
        HashMap<String, Integer> p2 = passage2.getWordFrequencies();
        for(String s: p1.keySet()){
            int p1Freq = p1.get(s);
            int p2Freq = p2.getOrDefault(s, 0);
            dotProduct += (double) p1Freq * p2Freq;
            p1Mag += Math.pow(p1Freq, 2);
        }
        for(int p2Freq : p2.values()){
            p2Mag += Math.pow(p2Freq, 2);
        }
        double cos;
        if(p1Mag == 0 || p2Mag == 0)
            cos = 0;
        else
            cos = dotProduct / (Math.sqrt(p1Mag) * Math.sqrt(p2Mag));
        passage1.getSimilarTitle().put(passage2.getTitle(), cos);
        passage2.getSimilarTitle().put(passage1.getTitle(), cos);
        return cos;
    }

    /**Returns the HashMap frequency of words.
     *
     * @return frequency of words.
     */
    public HashMap<String, Integer> getWordFrequencies(){
        return wordFrequencies;
    }

    /**Returns the words in the passage.
     *
     * @return Set of words in passage.
     */
    public Set<String> getWords(){
        return wordFrequencies.keySet();
    }

    /**Returns the title of the passage.
     *
     * @return Title of passage.
     */
    public String getTitle(){
        return title;
    }

    /**Sets the title of the passage.
     *
     * @param title title to set.
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**Returns the word count of the passage.
     *
     * @return word count.
     */
    public int getWordCount(){
        return wordCount;
    }

    /**Sets the word count of the passage.
     *
     * @param wordCount word count to set.
     */
    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    /**Returns the mapping of passage to percentage of similarity.
     *
     * @return HashMap of Passage to Similarity.
     */
    public HashMap<String, Double> getSimilarTitle(){
        return similarTitles;
    }

    /**Sets the mapping of passage to percentage of similarity.
     *
     * @param similarTitles HashMap to set.
     */
    public void setSimilarTitle(HashMap<String, Double> similarTitles){
        this.similarTitles = similarTitles;
    }

    /**Returns the title, word count, and similar titles of a passage.
     *
     * @return String representation of the details of a passage.
     */
    public String toString(){
        StringBuilder res = new StringBuilder();
        res.append(title + "\n Word Count:" + wordCount + "\n Similar Titles:"
          + similarTitles.toString() + "\n");
        return res.toString();
    }


}
