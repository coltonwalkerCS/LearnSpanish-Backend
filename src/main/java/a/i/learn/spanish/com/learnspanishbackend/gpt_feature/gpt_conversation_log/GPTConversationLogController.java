package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log;

import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/gptconversationlog")
public class GPTConversationLogController {
    @Autowired
    GPTConversationLogService gptConversationLogService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/create:{username}")
    public ResponseEntity<?> createGPTConversationLog(@PathVariable String username) {
        return new ResponseEntity<>(gptConversationLogService.createGPTConversationLog(username), HttpStatus.CREATED);
    }

    @GetMapping("/id:{id}")
    public ResponseEntity<?> getGPTConversationLog(@PathVariable Long id) {
        return new ResponseEntity<>(gptConversationLogService.getGPTConversationLog(id), HttpStatus.OK);
    }

}
