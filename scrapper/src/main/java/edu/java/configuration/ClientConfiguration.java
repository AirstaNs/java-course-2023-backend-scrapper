package edu.java.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    final ProviderConfig properties;

    @Autowired
    public ClientConfiguration(ProviderConfig properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient githubWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(properties.getGithub()).build();
    }

    @Bean
    public WebClient stackOverflowWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(properties.getStackoverflow()).build();
    }
}
