import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.io.File;
import java.util.Set;
import java.util.HashSet;

public class Passage {
    private int wordCount;
    private String title;
    private HashMap<String, Double> similarTitles;
    private HashMap<String, Integer> wordFrequencies;
    private static HashSet<String> stopWords = new HashSet<>();

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

    public Passage(String title, File file) throws IOException {
        this.title = title;
        wordFrequencies = new HashMap<>();
        similarTitles = new HashMap<>();
        wordCount = 0;
        parseFile(file);
    }

    public void parseFile(File file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while( (line = br.readLine()) != null) {
            String[] words = line.toLowerCase().split("\\s+");
            for(String word : words){
                word = word.trim().replaceAll("[^a-z]", "");
                wordCount++;
                if(word.isEmpty() || stopWords.contains(word))
                    continue;
                wordFrequencies.put(word, wordFrequencies.getOrDefault(word, 0)+1);
            }
        }
    }

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

    public HashMap<String, Integer> getWordFrequencies(){
        return wordFrequencies;
    }

    public Set<String> getWords(){
        return wordFrequencies.keySet();
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public int getWordCount(){
        return wordCount;
    }

    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    public HashMap<String, Double> getSimilarTitle(){
        return similarTitles;
    }

    public void setSimilarTitle(HashMap<String, Double> similarTitles){
        this.similarTitles = similarTitles;
    }

    public String toString(){
        StringBuilder res = new StringBuilder();
        String format = "%20s | %d";
        res.append(String.format(format, "Text (title)"));
        return "";
    }


}
