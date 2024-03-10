package edu.java.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;

@Getter
@ConfigurationProperties(prefix = "provider", ignoreUnknownFields = false)
public class ProviderConfig {

    private final String github;
    private final String stackoverflow;

    @ConstructorBinding
    public ProviderConfig(
        @DefaultValue("https://api.github.com") String github,
        @DefaultValue("https://api.stackexchange.com/2.3") String stackoverflow) {
        this.github = github;
        this.stackoverflow = stackoverflow;
    }
}
