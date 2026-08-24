import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

public class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word, int freq) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.endOfWord = true;
        current.freq = freq;
    }

    public boolean search(String word) {
        TrieNode node = findNode(word);
        return node != null && node.endOfWord;
    }

    // Ab yeh frequency ke hisaab se SORTED list return karega
    public List<String> getSuggestions(String prefix) {
        List<WordFreq> results = new ArrayList<>();
        TrieNode node = findNode(prefix);
        if (node == null) return new ArrayList<>();

        collectWords(node, prefix, results);

        // Frequency ke basis pe descending order mein sort karo
        results.sort(Comparator.comparingInt((WordFreq wf) -> wf.freq).reversed());

        // Sirf words nikaal ke return karo (frequency ab UI ko dikhani nahi)
        List<String> sortedWords = new ArrayList<>();
        for (WordFreq wf : results) {
            sortedWords.add(wf.word);
        }
        return sortedWords;
    }

    private TrieNode findNode(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return null;
        }
        return current;
    }

    private void collectWords(TrieNode node, String prefix, List<WordFreq> results) {
        if (node.endOfWord) {
            results.add(new WordFreq(prefix, node.freq));
        }
        for (char ch : node.children.keySet()) {
            collectWords(node.children.get(ch), prefix + ch, results);
        }
    }

    // Chhota helper class — word aur uski frequency ek saath store karne ke liye
    private static class WordFreq {
        String word;
        int freq;

        WordFreq(String word, int freq) {
            this.word = word;
            this.freq = freq;
        }
    }

    // Poori trie ke saare words nikalne ke liye (fuzzy match ke liye chahiye)
    private void collectAllWords(TrieNode node, String prefix, List<String> allWords) {
        if (node.endOfWord) {
            allWords.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            collectAllWords(node.children.get(ch), prefix + ch, allWords);
        }
    }

    // Typo-tolerant suggestions — Levenshtein Distance use karke
    public List<String> getFuzzySuggestions(String input, int maxDistance) {
        List<String> allWords = new ArrayList<>();
        collectAllWords(root, "", allWords);  // root se poori tree explore karo

        List<String> fuzzyMatches = new ArrayList<>();
        for (String word : allWords) {
            int distance = LevenshteinTest.calculateDistance(input, word);
            if (distance <= maxDistance) {
                fuzzyMatches.add(word);
            }
        }
        return fuzzyMatches;
    }
}