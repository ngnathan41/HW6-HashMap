import java.util.HashMap;
import java.util.ArrayList;
/** The FrequencyList class represents a word and the frequency of the word in a Passage.
 * @author Nathan Ng
 *  email: nathan.ng@stonybrook.edu
 *  ID: 116188023
 *  Recitation: 4
 */
public class FrequencyList {
    private String word;
    private ArrayList<Integer> frequencies;
    private HashMap<String, Integer> passageIndices;

    /**Instantiates a FrequencyList using a given word and list of passages.
     *
     * @param word Word of FrequencyList.
     * @param passages Passages to add to frequency list.
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        this.frequencies = new ArrayList<>();
        this.passageIndices = new HashMap<>();
        for(Passage passage: passages){
            addPassage(passage);
        }
    }

    /**Adds a passage to the frequency list.
     *
     * @param p Passage to add to frequency list.
     */
    public void addPassage(Passage p){
        int frequency = p.getWordFrequencies().getOrDefault(word, 0);
        if (passageIndices.containsKey(p.getTitle())){
            int index = passageIndices.get(p.getTitle());
            frequencies.set(index, frequency);
        }
        else{
            passageIndices.put(p.getTitle(), frequencies.size());
            frequencies.add(frequency);
        }
    }

    /**Returns the frequency of the word of the FrequencyList of a passage.
     *
     * @param p Passage to get frequency of word of.
     * @return Frequency of word in the Passage.
     */
    public int getFrequency(Passage p){
        if (passageIndices.containsKey(p.getTitle())){
            int index = passageIndices.get(p.getTitle());
            return frequencies.get(index);
        }
        return 0;
    }
}
