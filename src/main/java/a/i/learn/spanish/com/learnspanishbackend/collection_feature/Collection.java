package a.i.learn.spanish.com.learnspanishbackend.collection_feature;

import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.Flashcard;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "collection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private Instant lastAccessed;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Flashcard> flashcardIds;

    public Collection(String title, String description, User user) {
        this.title = title;
        this.description = description;
        this.user = user;
        this.lastAccessed = Instant.now();
    }

    public Collection(String title, User user, List<Flashcard> flashcardsIds) {
        this.title = title;
        this.user = user;
        this.flashcardIds = flashcardsIds;
    }
}
