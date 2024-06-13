package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPTConversationLogRepository extends JpaRepository<GPTConversationLog, Long> {

}
