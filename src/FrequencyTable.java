import java.util.HashMap;
import java.util.ArrayList;
/**The FrequencyTable class contains a map of words and it's associated FrequencyList.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class FrequencyTable {
    private HashMap<String, FrequencyList> wordList;

    /**Instantiates a FrequencyTable.
     *
     */
    public FrequencyTable() {
        this.wordList = new HashMap<>();
    }

    /**Builds a FrequencyTable from a list of Passages.
     *
     * @param passages List of Passages.
     * @return FrequencyTable that represents the list of Passages.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable table = new FrequencyTable();
        for (Passage passage : passages) {
            table.addPassage(passage);
        }
        return table;
    }

    /**Adds a passage to the frequency list. Adds or updagtes the proper frequency list to the frequency table.
     *
     * @param p Passage to add to frequency table.
     * @throws IllegalArgumentException indicates that Passage is null or is empty.
     */
    public void addPassage(Passage p) throws IllegalArgumentException {
        if(p == null || p.getWordCount() == 0)
            throw new IllegalArgumentException();
        for(String word: p.getWords()){
            wordList.putIfAbsent(word, new FrequencyList(word, new ArrayList<>()));
            wordList.get(word).addPassage(p);
        }
    }

    /**Returns the frequency of a word in a passage.
     *
     * @param word Word to find frequency of.
     * @param p Passage to find words from.
     * @return Frequency of the word in the passage.
     * @throws IllegalArgumentException Indicates that Passage is null or is empty.
     */
    public int getFrequency(String word, Passage p) throws IllegalArgumentException {
        if(p == null || p.getWordCount() == 0)
            throw new IllegalArgumentException();
        FrequencyList list = wordList.get(word);
        return (list !=null) ? list.getFrequency(p) : 0;
    }
}
