import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class VocabularyManager {
    private final String DATABASE_FILE = "src/vocabulary.txt";
    private List<VocabularyData> vocabulary;

    public VocabularyManager() {
        vocabulary = new ArrayList<>();
        loadVocabulary();
    }

    public static class VocabularyData {
        private String word;
        private String definition;

        public VocabularyData(String word, String definition) {
            this.word = word.toLowerCase();
            this.definition = definition;
        }

        public String getWord() {
            return word;
        }

        public String getDefinition() {
            return definition;
        }

        public void setDefinition(String definition) {
            this.definition = definition;
        }

        @Override
        public String toString() {
            return word + "#" + definition;
        }
    }

    private void loadVocabulary() {
        try {
            if (!Files.exists(Paths.get(DATABASE_FILE))) {
                createDefaultVocabulary();
                return;
            }

            List<String> lines = Files.readAllLines(Paths.get(DATABASE_FILE));
            vocabulary.clear();

            for (String line : lines) {
                if (line.trim().isEmpty())
                    continue;

                String[] parts = line.split("#", 2);
                if (parts.length == 2) {
                    vocabulary.add(new VocabularyData(parts[0].trim(), parts[1].trim()));
                }
            }

            vocabulary.sort((a, b) -> a.getWord().compareTo(b.getWord()));

        } catch (IOException e) {
            System.err.println("Error loading vocabulary: " + e.getMessage());
            createDefaultVocabulary();
        }
    }

    private void createDefaultVocabulary() {
        vocabulary.clear();
        vocabulary.add(new VocabularyData("apple", "A round fruit that grows on trees"));
        vocabulary.add(new VocabularyData("computer", "An electronic device for processing data"));
        vocabulary.add(new VocabularyData("vocabulary", "A collection of words and their meanings"));
        saveVocabulary();
    }

    public String searchWord(String word) {
        if (word == null || word.trim().isEmpty()) {
            return null;
        }
        String searchTerm = word.toLowerCase().trim();

        int index = binarySearch(searchTerm);
        if (index >= 0) {
            return vocabulary.get(index).getDefinition();
        }

        return null;
    }

    private int binarySearch(String word) {
        int left = 0;
        int right = vocabulary.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = vocabulary.get(mid).getWord().compareTo(word);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return -1;
    }

    public boolean addWord(String word, String definition) {
        if (word == null || word.trim().isEmpty() || definition == null || definition.trim().isEmpty()) {
            return false;
        }
        String normalizedWord = word.toLowerCase().trim();

        if (binarySearch(normalizedWord) >= 0) {
            return false;
        }

        vocabulary.add(new VocabularyData(normalizedWord, definition.trim()));
        vocabulary.sort((a, b) -> a.getWord().compareTo(b.getWord()));

        return saveVocabulary();
    }

    public boolean updateWord(String word, String newDefinition) {
        if (word == null || word.trim().isEmpty() || newDefinition == null || newDefinition.trim().isEmpty()) {
            return false;
        }

        String normalizedWord = word.toLowerCase().trim();
        int index = binarySearch(normalizedWord);

        if (index >= 0) {
            vocabulary.get(index).setDefinition(newDefinition.trim());
            return saveVocabulary();
        }

        return false;
    }

    private boolean saveVocabulary() {
        try {
            if (Files.exists(Paths.get(DATABASE_FILE))) {
                Files.copy(Paths.get(DATABASE_FILE), Paths.get(DATABASE_FILE + ".backup"));
            }

            try (PrintWriter writer = new PrintWriter(new FileWriter(DATABASE_FILE))) {
                for (VocabularyData entry : vocabulary) {
                    writer.println(entry.toString());
                }
            }

            return true;
        } catch (IOException e) {
            System.err.println("Error saving vocabulary: " + e.getMessage());
            return false;
        }
    }

    public List<String> getAllWords() {
        return vocabulary.stream()
                .map(VocabularyData::getWord)
                .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
