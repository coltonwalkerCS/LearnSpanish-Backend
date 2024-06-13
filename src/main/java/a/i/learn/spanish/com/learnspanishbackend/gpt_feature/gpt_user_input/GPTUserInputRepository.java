package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_user_input;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPTUserInputRepository extends JpaRepository<GPTUserInput, Long> {

}
