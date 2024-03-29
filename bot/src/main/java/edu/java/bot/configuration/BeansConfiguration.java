package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Bot API", description = "Bot API", version = "1.0.0"))
public class BeansConfiguration {

    @Bean
    public ResourceBundleMessageSource messageSourceResourceBundle() {
        ResourceBundleMessageSource messageSourceResourceBundle = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("message.yml"));
        messageSourceResourceBundle.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        return messageSourceResourceBundle;
    }

    @Bean
    public TelegramBot telegramBot(ApplicationConfig config) {
        return new TelegramBot(config.telegramToken());
    }
}
