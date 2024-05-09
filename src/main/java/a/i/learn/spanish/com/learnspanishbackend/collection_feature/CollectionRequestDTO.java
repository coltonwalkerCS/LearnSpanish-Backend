package a.i.learn.spanish.com.learnspanishbackend.collection_feature;

import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.Flashcard;
import lombok.Getter;

import java.util.List;

public class CollectionRequestDTO {
    @Getter
    private String title;

    @Getter
    private String description;

    @Getter
    private String username;

    @Getter
    private List<Flashcard> flashcards;
}
