import java.util.HashMap;
import java.util.ArrayList;
public class FrequencyTable {
    private HashMap<String, FrequencyList> wordList;

    public FrequencyTable() {
        this.wordList = new HashMap<>();
    }
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable table = new FrequencyTable();
        for (Passage passage : passages) {
            table.addPassage(passage);
        }
        return table;
    }

    public void addPassage(Passage p) throws IllegalArgumentException {
        if(p == null || p.getWordCount() == 0)
            throw new IllegalArgumentException();
        for(String word: p.getWords()){
            wordList.putIfAbsent(word, new FrequencyList(word, new ArrayList<>()));
            wordList.get(word).addPassage(p);
        }
    }

    public int getFrequency(String word, Passage p) throws IllegalArgumentException {
        if(p == null)
            throw new IllegalArgumentException();
        FrequencyList list = wordList.get(word);
        return (list !=null) ? list.getFrequency(p) : 0;
    }
}
