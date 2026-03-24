import java.io.*;
import java.util.*;

public class TagExtractor {

    public static Map<String, Integer> extractTags(File textFile, File stopFile) throws IOException {

        Map<String, Integer> wordMap = new TreeMap<>();
        Set<String> stopWords = new HashSet<>();

        BufferedReader stopReader = new BufferedReader(new FileReader(stopFile));
        String line;
        while ((line = stopReader.readLine()) != null) {
            stopWords.add(line.trim().toLowerCase());
        }
        stopReader.close();

        BufferedReader reader = new BufferedReader(new FileReader(textFile));

        while ((line = reader.readLine()) != null) {

            line = line.replaceAll("[^a-zA-Z ]", "").toLowerCase();

            String[] words = line.split("\\s+");

            for (String word : words) {
                if (word.isEmpty()) continue;

                if (stopWords.contains(word)) continue;
                wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
            }
        }

        reader.close();
        return wordMap;
    }
}