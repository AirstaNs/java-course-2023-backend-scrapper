package edu.java.bot.configuration;

import com.pengrad.telegrambot.TelegramBot;
import edu.java.bot.commands.Command;
import edu.java.bot.commands.HelpCommand;
import edu.java.bot.commands.ListCommand;
import edu.java.bot.commands.StartCommand;
import edu.java.bot.commands.TrackCommand;
import edu.java.bot.commands.UntrackCommand;
import edu.java.bot.resolver.TextResolver;
import edu.java.bot.service.BotService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BeansConfiguration {

    @Bean
    public TelegramBot telegramBot(ApplicationConfig config) {
        return new TelegramBot(config.telegramToken());
    }

    @Bean
    public ResourceBundleMessageSource messageSourceResourceBundle() {
        ResourceBundleMessageSource messageSourceResourceBundle = new ResourceBundleMessageSource();
        YamlPropertiesFactoryBean yamlPropertiesFactoryBean = new YamlPropertiesFactoryBean();
        yamlPropertiesFactoryBean.setResources(new ClassPathResource("message.yml"));
        messageSourceResourceBundle.setCommonMessages(yamlPropertiesFactoryBean.getObject());
        return messageSourceResourceBundle;
    }

    @Bean
    public List<Command> commandList(TextResolver textResolver, BotService botService) {
        List<Command> commands = new ArrayList<>();
        commands.add(new HelpCommand(textResolver, commands));
        commands.add(new StartCommand(textResolver, botService));
        commands.add(new TrackCommand(textResolver, botService));
        commands.add(new UntrackCommand(textResolver, botService));
        commands.add(new ListCommand(textResolver, botService));
        return commands;
    }
}
