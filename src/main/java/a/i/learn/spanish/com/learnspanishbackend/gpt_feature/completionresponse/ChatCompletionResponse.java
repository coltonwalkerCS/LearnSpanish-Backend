package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse;

import lombok.Data;
import java.util.List;

@Data
public class ChatCompletionResponse {
    String id;
    String object;
    Long createdOn;
    String model;
    String system_fingerprint;
    List<ChatCompletionResponseChoice> choices;
    ChatCompletionResponseUsage usage;
}
