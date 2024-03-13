package edu.java.bot.configuration;

import edu.java.bot.client.scrapper.ScrapperClient;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
public class ScrapperClientConfiguration {

    @Value("${scrapper.url:http://localhost:8080}")
    private String scrapperUrl;

    @Bean
    public ScrapperClient scrapperClient(WebClient.Builder webClientBuilder) {
        WebClient webClient = webClientBuilder
            .defaultStatusHandler(httpStatusCode -> true, clientResponse -> Mono.empty())
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .baseUrl(scrapperUrl)
            .build();

        var proxy = HttpServiceProxyFactory
            .builderFor(WebClientAdapter.create(webClient))
            .build();
        return proxy.createClient(ScrapperClient.class);
    }

}
