import java.io.File;
import java.util.Set;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
public class TextAnalyzer {
    private static FrequencyTable frequencyTable;
    public Set<String> stopWords;
    private static Scanner sc;

    public static void main(String[] args) {
        sc = new Scanner(System.in);
        frequencyTable = new FrequencyTable();

        double similarity;
        while (true) {
            System.out.println("Enter the similarity percentage: ");
            try {
                similarity = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid similarity percentage");
            }
        }

        System.out.println("Enter the directory of a folder of text files:");
        String path = sc.nextLine();
        File folder = new File(path);

        File stopWords = null;
        ArrayList<File> textFiles = new ArrayList<>();
        for (File file : folder.listFiles()) {
            if (file.getName().equalsIgnoreCase("StopWords.txt")) {
                stopWords = file; // Identify StopWords.txt
            } else if (file.getName().endsWith(".txt")) {
                textFiles.add(file); // Add other text files
            }
        }
        Passage.initiate(stopWords);

        ArrayList<Passage> passages = new ArrayList<>();
        for (File file : textFiles) {
            try {
                passages.add(new Passage(file.getName().replaceFirst("[.][^.]+$", ""), file));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        frequencyTable = FrequencyTable.buildTable(passages);
        printSimilarity(passages, similarity);
        similarAuthor(passages, similarity);

    }

    private static void printSimilarity(ArrayList<Passage> passages, double threshold) {
        System.out.printf("%-30s | %s%n", "Text (title)", "Similarities (%)");
        Collections.sort(passages, (p1, p2) -> p1.getTitle().compareToIgnoreCase(p2.getTitle()));
        ArrayList<String> suspectedAuthors = new ArrayList<>();

        for (Passage p1 : passages) {
            ArrayList<String> similarities = new ArrayList<>();
            for (Passage p2 : passages) {
                if (!p1.equals(p2)) {
                    double similarity = Passage.cosineSimilarity(p1, p2) * 100;
                    similarities.add(p2.getTitle() + "(" + String.format("%.0f%%", similarity) + ")");
                }
            }
            System.out.println("--------------------------------------------------------------------------------");
            System.out.printf("%-30s |", p1.getTitle());
            System.out.println(similarities.get(0) + ", " + similarities.get(1) + ", ");
            System.out.printf("%-30s | %s%n", "", similarities.get(2) + ", " + similarities.get(3));
        }
    }

    private static void similarAuthor(ArrayList<Passage> passages, double threshold){
        System.out.println("Suspected Texts With Same Authors\n" +
                "--------------------------------------------------------------------------------");
        for(int i = 0; i < passages.size(); i++){
            for(int j = i + 1; j < passages.size(); j++){
                Passage p1 = passages.get(i);
                Passage p2 = passages.get(j);
                int similarity = (int) Math.round(Passage.cosineSimilarity(p1, p2) * 100);
                if(similarity>= threshold* 100){
                    System.out.println("'" + p1.getTitle() + "' and '" + p2.getTitle() + "' may have the same author (" + similarity + "% similar).");
                }
            }
        }
    }
}