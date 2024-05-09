package a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse;

import lombok.Data;

@Data
public class ChatCompletionResponseChoice {
    ChatCompletionResponseChoiceMessage message;
    Integer index;
    String finishReason;
}
