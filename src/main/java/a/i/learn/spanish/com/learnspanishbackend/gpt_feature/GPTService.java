package a.i.learn.spanish.com.learnspanishbackend.gpt_feature;


import a.i.learn.spanish.com.learnspanishbackend.collection_feature.Collection;
import a.i.learn.spanish.com.learnspanishbackend.collection_feature.CollectionRepository;
import a.i.learn.spanish.com.learnspanishbackend.flashcard_feature.Flashcard;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse.ChatCompletionResponse;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse.ChatCompletionResponseChoice;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log.GPTConversationLog;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_conversation_log.GPTConversationLogService;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_model_output.GPTModelOutput;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_model_output.GPTModelOutputRepository;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_user_input.GPTUserInput;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.gpt_user_input.GPTUserInputRepository;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.User;
import a.i.learn.spanish.com.learnspanishbackend.user_feature.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
public class GPTService {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    GPTConversationLogService gptConversationLogService;

    @Autowired
    GPTUserInputRepository gptUserInputRepository;

    @Autowired
    GPTModelOutputRepository gptModelOutputRepository;


    private WebClient webClient;

    @Value("${gpt.secret}")
    String SECRET;

    final private String GPT_URL = "https://api.openai.com/v1/chat/completions";

    public void GPTRequest(String username, String request, Collection collection) throws JsonProcessingException {
        String gptOutput = getGPTOutput(request, collection);

        Optional<User> optionalUser = userRepository.findUserByUsername(username);

        if (optionalUser.isPresent()) {
            Optional<GPTConversationLog> savedConversationLog = gptConversationLogService.createGPTConversationLog(username);

            GPTUserInput gptUserInput = new GPTUserInput(request);
            GPTModelOutput gptModelOutput = new GPTModelOutput(gptOutput);

            GPTUserInput savedUserInput = gptUserInputRepository.save(gptUserInput);
            GPTModelOutput savedModelOutput = gptModelOutputRepository.save(gptModelOutput);



//            Collection savedCollection = collectionRepository.save(collection);
//            return Optional.of(savedCollection);


        } else {
            // TODO: ADD ERROR HANDLING FOR NON LOGGED-IN USER
            System.out.println("USER NEEDS TO LOGGIN --- ERROR");
        }

        return;
    }

    private String getGPTOutput(String request, Collection collection) throws JsonProcessingException {

        String flashcardVocab = getRandomVocabFromCollection(collection);

        webClient = WebClient.create();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.2);

        requestBody.put("messages", Arrays.asList(
                Map.of("role", "system",
                        "content", "You are a Spanish story teller. You will take the users request and create a story based on it."),
                Map.of("role", "system",
                        "content", "The content should be at an A1 learning level."),
                Map.of("role", "system",
                        "content", "You will include vocabulary from this list: " + flashcardVocab),
                Map.of(
                        "role", "user",
                        "content", request)
        ));

        ObjectMapper objectMapper = new ObjectMapper();

        Mono<ChatCompletionResponse> completionResponseMono = webClient.post()
                .uri(GPT_URL)
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setBearerAuth(SECRET);
                })
                .bodyValue(objectMapper.writeValueAsString(requestBody))
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode httpStatus = clientResponse.statusCode();
                    if (httpStatus.is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ChatCompletionResponse.class);
                    } else {
                        Mono<String> stringMono = clientResponse.bodyToMono(String.class);
                        stringMono.subscribe(s -> {
                            System.err.println("Response from Open AI API " + s);
                        });
                        System.err.println("Error occurred while invoking Open AI API");
                        return Mono.error(new Exception(
                                "Error occurred while generating wordage"));
                    }
                });

        ChatCompletionResponse completionResponse = completionResponseMono.block();
        List<ChatCompletionResponseChoice> choices = completionResponse.getChoices();
        ChatCompletionResponseChoice aChoice = choices.get(0);
        return aChoice.getMessage().getContent();
    }

    private String getRandomVocabFromCollection(Collection collection) {
        List<Flashcard> fiveFlashcards = new ArrayList<Flashcard>();

        // Get random words from collection
        if (collection.getId() != null) {
            Optional<Collection> collectionOpt = collectionRepository.findById(collection.getId());
            if (collectionOpt.isPresent()) {
                List<Flashcard> allFlashcards = collection.getFlashcardIds();
                fiveFlashcards = allFlashcards.stream().limit(5).toList();
            }
        }
        String gpt_vocab_input = "";
        for (Flashcard flash: fiveFlashcards) {
            gpt_vocab_input = gpt_vocab_input.concat(flash.getSideOne() + ", ");
        }

        return gpt_vocab_input.substring(0, gpt_vocab_input.length()-2);
    }

    public Mono<ChatCompletionResponse> getGptRequest() throws JsonProcessingException {
        webClient = WebClient.create();

        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.2);

        requestBody.put("messages", Arrays.asList(
                Map.of("role", "system",
                        "content", "You are a Spanish story teller. You will take the users request and create a story based on it."),
                Map.of(
                        "role", "user",
                        "content", "What year have the Cheifs won the Super bowl?")
        ));

        ObjectMapper objectMapper = new ObjectMapper();

        Mono<ChatCompletionResponse> completionResponseMono = webClient.post()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setBearerAuth(SECRET);
                })
                .bodyValue(objectMapper.writeValueAsString(requestBody))
                .exchangeToMono(clientResponse -> {
                    HttpStatusCode httpStatus = clientResponse.statusCode();
                    if (httpStatus.is2xxSuccessful()) {
                        return clientResponse.bodyToMono(ChatCompletionResponse.class)
                                .doOnNext(chatCompletionResponse -> System.out.println("Body: " + chatCompletionResponse));
                    } else {
                        Mono<String> stringMono = clientResponse.bodyToMono(String.class);
                        stringMono.subscribe(s -> {
                            System.err.println("Response from Open AI API " + s);
                        });
                        System.err.println("Error occurred while invoking Open AI API");
                        return Mono.error(new Exception(
                                "Error occurred while generating wordage"));
                    }
                });

        ChatCompletionResponse completionResponse = completionResponseMono.block();
        List<ChatCompletionResponseChoice> choices = completionResponse.getChoices();
        ChatCompletionResponseChoice aChoice = choices.get(0);
        System.out.println(aChoice.getMessage().getContent());
        return completionResponseMono;
    }

}
