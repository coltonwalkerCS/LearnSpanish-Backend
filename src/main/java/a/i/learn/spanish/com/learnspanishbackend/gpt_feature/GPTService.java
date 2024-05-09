package a.i.learn.spanish.com.learnspanishbackend.gpt_feature;

import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse.ChatCompletionResponse;
import a.i.learn.spanish.com.learnspanishbackend.gpt_feature.completionresponse.ChatCompletionResponseChoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.springframework.beans.factory.annotation.Value;

import java.util.*;

@Service
public class GPTService {

    private WebClient webClient;

    @Value("${gpt.secret}")
    String secret;

    public Mono<ChatCompletionResponse> getGptRequest() throws JsonProcessingException {

        webClient = WebClient.create();

        String url = "https://api.openai.com/v1/chat/completions";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "gpt-3.5-turbo");
        requestBody.put("max_tokens", 200);
        requestBody.put("temperature", 0.2);

        requestBody.put("messages", Arrays.asList(
                Map.of("role", "system",
                        "content", "You are a helpful assistant."),
                Map.of(
                        "role", "user",
                        "content", "What year have the Cheifs won the Super bowl?")
        ));

        ObjectMapper objectMapper = new ObjectMapper();
        Mono<ChatCompletionResponse> completionResponseMono = webClient.post()
                .uri(url)
                .headers(httpHeaders -> {
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    httpHeaders.setBearerAuth(secret);
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
