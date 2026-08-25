package autocomplete_backend;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserClickRepository extends JpaRepository<UserClick, Long> {

    // Spring khud is method ka implementation bana dega naam dekh ke!
    Optional<UserClick> findByUserIdAndWord(String userId, String word);
}