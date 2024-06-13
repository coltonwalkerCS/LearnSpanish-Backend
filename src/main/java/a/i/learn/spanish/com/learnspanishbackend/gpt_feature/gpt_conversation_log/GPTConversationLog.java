package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log;

import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_model_output.GPTModelOutput;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_user_input.GPTUserInput;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "gpt_conversation_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GPTConversationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user")
    @JsonBackReference
    User user;

    @OneToMany(mappedBy = "gptConversationLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GPTUserInput> user_input;

    @OneToMany(mappedBy = "gptConversationLog", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<GPTModelOutput> model_output;

    public GPTConversationLog(User user) {
        this.user = user;
    }
}
