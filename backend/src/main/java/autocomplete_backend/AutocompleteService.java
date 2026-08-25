package autocomplete_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class AutocompleteService {

    private Trie trie;

    @Autowired
    private UserClickRepository userClickRepository;

    @PostConstruct
    public void init() {
        trie = new Trie();
        loadWordsFromFile();
    }

    private void loadWordsFromFile() {
        try {
            ClassPathResource resource = new ClassPathResource("words.txt");
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));

            String line;
            int rank = 10000; // pehla word ko sabse zyada frequency milegi
            while ((line = reader.readLine()) != null) {
                String word = line.trim();
                if (!word.isEmpty()) {
                    trie.insert(word, rank); // rank hi frequency ki tarah use kar rahe hain
                    rank--; // agla word thoda kam frequency wala hoga
                }
            }
            reader.close();
            System.out.println("Dataset loaded successfully!");
        } catch (Exception e) {
            System.err.println("Error loading dataset: " + e.getMessage());
            // Fallback - agar file na mile, kam se kam kuch sample words toh honi chahiye
            trie.insert("cat", 50);
            trie.insert("car", 80);
            trie.insert("card", 30);
            trie.insert("care", 40);
            trie.insert("dog", 60);
        }
    }

    public List<String> getSuggestions(String prefix) {
        return trie.getSuggestions(prefix);
    }

    public List<String> getFuzzySuggestions(String input, int maxDistance) {
        return trie.getFuzzySuggestions(input, maxDistance);
    }

    public List<String> getPersonalizedSuggestions(String prefix, String userId) {
        return trie.getPersonalizedSuggestions(prefix, userId, userClickRepository);
    }

    public void recordSelection(String userId, String word) {
        Optional<UserClick> existing = userClickRepository.findByUserIdAndWord(userId, word);

        if (existing.isPresent()) {
            UserClick click = existing.get();
            click.setClickCount(click.getClickCount() + 1);
            userClickRepository.save(click);
        } else {
            UserClick newClick = new UserClick(userId, word, 1);
            userClickRepository.save(newClick);
        }
    }
}