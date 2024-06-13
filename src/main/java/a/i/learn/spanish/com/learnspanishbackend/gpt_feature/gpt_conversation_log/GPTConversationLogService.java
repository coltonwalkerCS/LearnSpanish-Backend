package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log;

import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GPTConversationLogService {
    @Autowired
    GPTConversationLogRepository gptConversationLogRepository;

    @Autowired
    UserRepository userRepository;

    public List<GPTConversationLog> allGPTConversationLogs() { return gptConversationLogRepository.findAll(); }

    public Optional<GPTConversationLog> getGPTConversationLog(Long id) {
        return gptConversationLogRepository.findById(id);
    }

    public Optional<GPTConversationLog> createGPTConversationLog(String username){
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            GPTConversationLog gptConversationLog = new GPTConversationLog(user);

            GPTConversationLog savedGPTConversationLog = gptConversationLogRepository.save(gptConversationLog);
            return Optional.of(savedGPTConversationLog);
        }
        return Optional.empty();
    }

}
