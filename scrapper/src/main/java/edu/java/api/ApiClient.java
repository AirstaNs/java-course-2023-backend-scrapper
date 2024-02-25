package edu.java.api;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public abstract class ApiClient<T> {

    protected final WebClient webClient;

    protected ApiClient(WebClient webClient) {
        this.webClient = webClient;
    }

    protected ApiClient(String url) {
        this.webClient = WebClient.create(url);
    }


    protected T fetch(String uri, Class<T> type, T defaultValue) {
        return webClient.get()
                        .uri(uri)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .bodyToMono(type)
                        .onErrorReturn(defaultValue)
                        .block();
    }
}
