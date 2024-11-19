import java.util.HashMap;
import java.util.ArrayList;
public class FrequencyList {
    private String word;
    private ArrayList<Integer> frequencies;
    private HashMap<String, Integer> passageIndices;

    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;
        this.frequencies = new ArrayList<>();
        this.passageIndices = new HashMap<>();
        for(Passage passage: passages){
            addPassage(passage);
        }
    }

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

    public int getFrequency(Passage p){
        if (passageIndices.containsKey(p.getTitle())){
            int index = passageIndices.get(p.getTitle());
            return frequencies.get(index);
        }
        return 0;
    }
}
