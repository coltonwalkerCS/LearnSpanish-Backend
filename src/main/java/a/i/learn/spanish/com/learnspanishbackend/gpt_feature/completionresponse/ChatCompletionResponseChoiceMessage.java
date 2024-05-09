package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse;

import lombok.Data;

@Data
public class ChatCompletionResponseChoiceMessage {
    String role;
    String content;
}
