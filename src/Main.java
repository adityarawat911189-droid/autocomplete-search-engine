import java.util.List;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("cat", 50);
        trie.insert("car", 80);
        trie.insert("card", 30);
        trie.insert("care", 40);
        trie.insert("dog", 60);

        // Normal prefix suggestions
        List<String> suggestions = trie.getSuggestions("ca");
        System.out.println("Suggestions for 'ca': " + suggestions);

        // Fuzzy/typo suggestions
        List<String> fuzzy = trie.getFuzzySuggestions("acr", 2);
        System.out.println("Fuzzy suggestions for 'acr' (typo of 'car'): " + fuzzy);

        List<String> fuzzy2 = trie.getFuzzySuggestions("dawg", 2);
        System.out.println("Fuzzy suggestions for 'dawg' (typo of 'dog'): " + fuzzy2);
    }
}