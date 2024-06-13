package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_user_input;

import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log.GPTConversationLog;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_model_output.GPTModelOutput;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "gpt_user_input")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GPTUserInput {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user")
    @JsonBackReference
    User user;

    @ManyToOne
    @JoinColumn(name="gptConversationLog")
    @JsonBackReference
    GPTConversationLog gptConversationLog;

    @OneToOne
    @JoinColumn(name="gpt_model_output")
    GPTModelOutput gptModelOutput;

    private String input;

    private Instant lastAccessed;

    public GPTUserInput(String input) {
        this.input = input;
        this.lastAccessed = Instant.now();
    }

}
