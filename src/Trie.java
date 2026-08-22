import java.util.ArrayList;
import java.util.List;
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

    public List<String> getSuggestions(String prefix) {
        List<String> results = new ArrayList<>();
        TrieNode node = findNode(prefix);
        if (node == null) return results;

        collectWords(node, prefix, results);
        return results;
    }

    private TrieNode findNode(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toCharArray()) {
            current = current.children.get(ch);
            if (current == null) return null;
        }
        return current;
    }

    private void collectWords(TrieNode node, String prefix, List<String> results) {
        if (node.endOfWord) {
            results.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            collectWords(node.children.get(ch), prefix + ch, results);
        }
    }
}
