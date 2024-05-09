package a.i.learn.spanish.com.learnspanishbackend.flashcard_feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/flashcard")
public class FlashcardController {
    @Autowired
    FlashcardService flashcardService;

}
