package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.dto.OptionalAnswer;
import edu.java.bot.resolver.TextResolver;
import edu.java.bot.service.BotService;
import java.util.Map;
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
        Long chatId = update.message().chat().id();
        OptionalAnswer<ListLinksResponse> optionalResponse = botService.listLinks(chatId);

        if (optionalResponse.isError()) {
            return new SendMessage(
                chatId,
                textResolver.resolve("command.list.error",
                    Map.of("error_message", optionalResponse.apiErrorResponse().description())
                )
            );
        }

        ListLinksResponse response = optionalResponse.answer();
        if (response == null || response.links().isEmpty()) {
            return new SendMessage(chatId, textResolver.resolve("command.list.empty"));
        }

        StringBuilder message = new StringBuilder();
        message.append(textResolver.resolve("command.list.main"));
        int id = 1;
        for (LinkResponse link : response.links()) {
            message.append(id).append(". ").append(link.url()).append("\n");
            id++;
        }
        return new SendMessage(chatId, message.toString()).disableWebPagePreview(true);
    }
}
