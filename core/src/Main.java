import java.util.List;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("cat", 50);
        trie.insert("car", 80);
        trie.insert("card", 30);
        trie.insert("care", 40);
        trie.insert("dog", 60);

        System.out.println("--- Normal cases ---");
        System.out.println("Suggestions for 'ca': " + trie.getSuggestions("ca"));
        System.out.println("Fuzzy for 'acr': " + trie.getFuzzySuggestions("acr", 2));

        System.out.println("\n--- Edge cases ---");

        // Case sensitivity check
        System.out.println("Suggestions for 'Ca' (capital C): " + trie.getSuggestions("Ca"));

        // Empty input
        System.out.println("Suggestions for '' (empty): " + trie.getSuggestions(""));

        // No match at all
        System.out.println("Suggestions for 'xyz': " + trie.getSuggestions("xyz"));
        System.out.println("Suggestions for 'Ca' (capital C): " + trie.getSuggestions("Ca"));
        System.out.println("Suggestions for '' (empty): " + trie.getSuggestions(""));
        // Fuzzy with no close match
        System.out.println("Fuzzy for 'xyz' (distance 2): " + trie.getFuzzySuggestions("xyz", 2));
    }
}