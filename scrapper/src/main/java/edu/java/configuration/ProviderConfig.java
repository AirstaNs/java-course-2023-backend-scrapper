package edu.java.configuration;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConfigurationProperties(prefix = "provider", ignoreUnknownFields = false)
public class ProviderConfig {

    private final String github;
    private final String stackoverflow;

    @ConstructorBinding
    public ProviderConfig(@NotBlank String github, @NotBlank String stackoverflow) {
        this.github = github;
        this.stackoverflow = stackoverflow;
    }
}
