package a.i.learn.spanish.com.learnspanishbackend.flashcard_feature;

import a.i.learn.spanish.com.learnspanishbackend.collection_feature.Collection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "flashcard")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Flashcard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name="collection")
    @JsonBackReference
    Collection collection;

    String sideOne;
    String sideTwo;
}
