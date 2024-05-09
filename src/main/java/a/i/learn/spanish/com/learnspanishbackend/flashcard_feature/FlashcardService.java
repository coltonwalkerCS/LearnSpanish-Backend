package a.i.learn.spanish.com.learnspanishbackend.flashcard_feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FlashcardService {
    @Autowired
    FlashcardRepository flashcardRepository;

}
