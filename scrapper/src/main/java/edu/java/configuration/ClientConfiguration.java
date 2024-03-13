package edu.java.configuration;

import edu.java.client.bot.BotClient;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.core.publisher.Mono;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Scrapper API", description = "Scrapper API", version = "1.0.0"))
public class ClientConfiguration {
    final ProviderConfig properties;

    @Value("${bot.url:http://localhost:8090}")
    private String botUrl;

    @Autowired
    public ClientConfiguration(ProviderConfig properties) {
        this.properties = properties;
    }

    @Bean
    public WebClient githubWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(properties.getGithub())
            .build();
    }

    @Bean
    public WebClient stackOverflowWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(properties.getStackoverflow())
            .build();
    }

    @Bean
    public BotClient botClient(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
            .defaultStatusHandler(httpStatusCode -> true, clientResponse -> Mono.empty())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(botUrl)
            .build();

        var proxy = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build();
        return proxy.createClient(BotClient.class);
    }
}
