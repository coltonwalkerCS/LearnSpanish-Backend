package a.i.learn.spanish.com.learnspanishbackend.collection_feature;

import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.Flashcard;
import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.FlashcardRepository;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    FlashcardRepository flashcardRepository;

    @Autowired
    UserRepository userRepository;

    public List<Collection> allCollections() { return collectionRepository.findAll(); }

    public Optional<Collection> getCollection(Long id) {
        Optional<Collection> collectionOpt = collectionRepository.findById(id);
        collectionOpt.ifPresent(collection -> {
            collection.setLastAccessed(Instant.now());
            collectionRepository.save(collection); // Save the updated entity
        });
        return collectionOpt;
    }

    public List<Collection> getUserCollections(User user) {return collectionRepository.findCollectionsByUser(user); }

    public List<Collection> recentCollections(User user) { return collectionRepository.findTop6ByUserOrderByLastAccessedDesc(user); }

    public Optional<Collection> createCollection(String title, String description, String username, List<Flashcard> flashcards) {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Collection collection = new Collection(title, description, user);

            for (Flashcard card : flashcards) {
                card.setCollection(collection);
            }
            collection.setFlashcardIds(flashcards);

            Collection savedCollection = collectionRepository.save(collection);
            return Optional.of(savedCollection);
        }
        return Optional.empty();
    }
}
