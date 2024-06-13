package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_model_output;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPTModelOutputRepository extends JpaRepository<GPTModelOutput, Long> {

}
