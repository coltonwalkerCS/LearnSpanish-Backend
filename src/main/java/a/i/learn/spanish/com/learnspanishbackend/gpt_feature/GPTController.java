package a.i.learn.spanish.com.learnspanishbackend.gpt_feature;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// GPT Controller
@RestController
@RequestMapping("/api/v1/gpt")
public class GPTController {

    @Autowired
    GPTService gptService;

    @GetMapping("")
    public void getGptResponse() throws JsonProcessingException {
        gptService.getGptRequest();
    }

}
