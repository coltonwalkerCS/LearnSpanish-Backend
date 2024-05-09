package a.i.learn.spanish.com.learnspanishbackend.collection_feature;

import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.Flashcard;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

@RestController
@RequestMapping("/api/v1/collections")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @Autowired
    UserRepository userRepository;

    // Creates a collection from the Collection DTO object
    @PostMapping("/createcollection")
    public ResponseEntity<?> createCollection(@RequestBody CollectionRequestDTO request) {
        String title = request.getTitle();
        String description = request.getDescription();
        String username = request.getUsername();
        List<Flashcard> flashcards = request.getFlashcards();
        return new ResponseEntity<>(collectionService.createCollection(title, description, username, flashcards), HttpStatus.CREATED);
    }

    // Gets all collections from the DB
    @GetMapping("")
    public List<Collection> getAllCollections() {
        return collectionService.allCollections();
    }

    // Gets the most recent 6 collections used by a specific user
    @GetMapping("/recent:{username}")
    public List<Collection> getMostRecentCollections(@PathVariable String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        List<Collection> usersCollections = new ArrayList<>();
        if (user.isPresent()) {
            usersCollections = collectionService.recentCollections(user.get());
        }
        return usersCollections;
    }

    // Gets all the collections made by the user
    @GetMapping("/username:{username}")
    public List<Collection> getUsersCollections(@PathVariable String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        List<Collection> usersCollections = new ArrayList<>();
        if (user.isPresent()) {
            usersCollections = collectionService.getUserCollections(user.get());
        }
        return usersCollections;
    }

    // Gets a specific collection given the id
    @GetMapping("/id:{id}")
    public ResponseEntity<?> getCollection(@PathVariable Long id) {
        return new ResponseEntity<>(collectionService.getCollection(id), HttpStatus.OK);
    }
}
