package a.i.learn.spanish.com.learnspanishbackend.gpt_feature;

import a.i.learn.spanish.com.learnspanishbackend.collection_feature.Collection;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/requestGPT")
    public void getGPTResponseTest(@RequestBody GPTRequestDTO request) throws JsonProcessingException {
        String username = request.getUsername();
        String inputRequest = request.getInputRequest();
        Collection collectionUsed = request.getCollection();

        gptService.GPTRequest(username, inputRequest, collectionUsed);
    }

}
