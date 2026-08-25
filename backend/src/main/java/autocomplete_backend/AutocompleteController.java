package autocomplete_backend;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AutocompleteController {

    private final AutocompleteService autocompleteService;

    public AutocompleteController(AutocompleteService autocompleteService) {
        this.autocompleteService = autocompleteService;
    }

    @GetMapping("/api/suggest")
    public List<String> suggest(@RequestParam String q) {
        return autocompleteService.getSuggestions(q);
    }

    @GetMapping("/api/suggest/fuzzy")
    public List<String> fuzzySuggest(
            @RequestParam String q,
            @RequestParam(defaultValue = "2") int maxDistance) {
        return autocompleteService.getFuzzySuggestions(q, maxDistance);
    }

    @GetMapping("/api/suggest/personalized")
    public List<String> personalizedSuggest(
            @RequestParam String q,
            @RequestParam String userId) {
        return autocompleteService.getPersonalizedSuggestions(q, userId);
    }

    @PostMapping("/api/select")
    public String recordSelection(
            @RequestParam String userId,
            @RequestParam String word) {
        autocompleteService.recordSelection(userId, word);
        return "Recorded: " + userId + " selected " + word;
    }
}