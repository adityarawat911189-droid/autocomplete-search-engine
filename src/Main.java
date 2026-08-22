import java.util.List;

public class Main {
    public static void main(String[] args) {
        Trie trie = new Trie();

        trie.insert("cat", 50);
        trie.insert("car", 80);
        trie.insert("card", 30);
        trie.insert("care", 40);
        trie.insert("dog", 60);

        List<String> suggestions = trie.getSuggestions("ca");
        System.out.println("Suggestions for 'ca': " + suggestions);

        System.out.println("Is 'car' a word? " + trie.search("car"));
        System.out.println("Is 'ca' a word? " + trie.search("ca"));
    }
}