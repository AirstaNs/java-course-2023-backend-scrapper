package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.model.Link;
import edu.java.bot.model.response.ListLinksResponse;
import edu.java.bot.resolver.TextResolver;
import edu.java.bot.service.BotService;
import org.springframework.stereotype.Component;

@Component
public class ListCommand extends AbstractCommand {

    private final BotService botService;

    public ListCommand(TextResolver textResolver, BotService botService) {
        super(textResolver);
        this.botService = botService;
    }

    @Override
    public String command() {
        return "/list";
    }

    @Override
    public String description() {
        return "command.list.description";
    }

    @Override
    public SendMessage handle(Update update) {
        ListLinksResponse response = botService.listLinks(update.message().chat().id());
        if (response.links() == null || response.links().isEmpty()) {
            return new SendMessage(update.message().chat().id(), textResolver.resolve("command.list.empty"));
        }
        StringBuilder message = new StringBuilder();
        message.append(textResolver.resolve("command.list.main"));
        int id = 1;
        for (Link link : response.links()) {
            message.append(id).append(". ").append(link.url()).append("\n");
            id++;
        }
        return new SendMessage(update.message().chat().id(), message.toString()).disableWebPagePreview(true);
    }
}
