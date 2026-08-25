package autocomplete_backend;

import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;

@Service
public class AutocompleteService {

    private Trie trie;

    @PostConstruct
    public void init() {
        trie = new Trie();
        // Sample data - baad mein isko dataset se load karenge
        trie.insert("cat", 50);
        trie.insert("car", 80);
        trie.insert("card", 30);
        trie.insert("care", 40);
        trie.insert("dog", 60);
    }

    public List<String> getSuggestions(String prefix) {
        return trie.getSuggestions(prefix);
    }
}