package autocomplete_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
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
        trie.insert("cat", 50);
        trie.insert("car", 80);
        trie.insert("card", 30);
        trie.insert("care", 40);
        trie.insert("dog", 60);
    }

    public List<String> getSuggestions(String prefix) {
        return trie.getSuggestions(prefix);
    }

    public List<String> getFuzzySuggestions(String input, int maxDistance) {
        return trie.getFuzzySuggestions(input, maxDistance);
    }

    // Personalized suggestions - database se personal frequency fetch karke
    public List<String> getPersonalizedSuggestions(String prefix, String userId) {
        return trie.getPersonalizedSuggestions(prefix, userId, userClickRepository);
    }

    // Jab user koi suggestion select kare - database mein save/update karo
    public void recordSelection(String userId, String word) {
        Optional<UserClick> existing = userClickRepository.findByUserIdAndWord(userId, word);

        if (existing.isPresent()) {
            // Pehle se hai - count badhao
            UserClick click = existing.get();
            click.setClickCount(click.getClickCount() + 1);
            userClickRepository.save(click);
        } else {
            // Naya entry banao
            UserClick newClick = new UserClick(userId, word, 1);
            userClickRepository.save(newClick);
        }
    }
}