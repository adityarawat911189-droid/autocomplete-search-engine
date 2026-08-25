package autocomplete_backend;

import jakarta.persistence.*;

@Entity
@Table(name = "user_clicks")
public class UserClick {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String word;
    private int clickCount;

    // Empty constructor - JPA ko chahiye hota hai
    public UserClick() {
    }

    public UserClick(String userId, String word, int clickCount) {
        this.userId = userId;
        this.word = word;
        this.clickCount = clickCount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }
}