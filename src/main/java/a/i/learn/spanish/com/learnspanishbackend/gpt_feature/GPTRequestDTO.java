package a.i.learn.spanish.com.learnspanishbackend.gpt_feature;

import a.i.learn.spanish.com.learnspanishbackend.collection_feature.Collection;
import lombok.Getter;

public class GPTRequestDTO {
    @Getter
    String username;

    @Getter
    String inputRequest;

    @Getter
    Collection collection;
}
