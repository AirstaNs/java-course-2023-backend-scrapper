package edu.java.api.stackOverflow;

import edu.java.api.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class StackOverflowClient extends ApiClient<StackOverflowResponse> {

    @Autowired
    public StackOverflowClient(WebClient stackOverflowWebClient) {
        super(stackOverflowWebClient);
    }

    public StackOverflowClient(String url) {
        super(url);
    }

    public StackOverflowResponse getQuestionInfo(long questionId) {
        String uri = "/questions/%d?site=stackoverflow".formatted(questionId);
        return fetch(uri, StackOverflowResponse.class, StackOverflowResponse.EMPTY);
    }
}
