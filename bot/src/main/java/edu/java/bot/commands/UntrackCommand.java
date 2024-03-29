package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.scrapper.dto.response.LinkResponse;
import edu.java.bot.client.scrapper.dto.response.ListLinksResponse;
import edu.java.bot.dto.OptionalAnswer;
import edu.java.bot.resolver.TextResolver;
import edu.java.bot.service.BotService;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class UntrackCommand extends AbstractCommand {
    private static final String UNTRACK_DATA_PREFIX = "untrack$";
    private final BotService botService;

    public UntrackCommand(TextResolver textResolver, BotService botService) {
        super(textResolver);
        this.botService = botService;
    }

    @Override
    public String command() {
        return "/untrack";
    }

    @Override
    public String description() {
        return "command.untrack.description";
    }

    @Override
    public boolean supports(Update update) {
        return super.supports(update)
               || update.callbackQuery() != null && update.callbackQuery().data().startsWith(UNTRACK_DATA_PREFIX);
    }

    @Override
    public SendMessage handle(Update update) {
        if (update.callbackQuery() != null) {
            return processCallbackQuery(update);
        }
        return processUserCommand(update);
    }

    private SendMessage processCallbackQuery(Update update) {
        String id = update.callbackQuery().data().substring(UNTRACK_DATA_PREFIX.length());
        long chatId = update.callbackQuery().from().id();
        OptionalAnswer<LinkResponse> response = botService.removeLinkFromUser(Long.parseLong(id), chatId);
        if (response.isError()) {
            return new SendMessage(chatId,
                textResolver.resolve("command.untrack.error",
                    Map.of("request_link", id, "error_message", response.apiErrorResponse().description())
                )
            );
        }
        return new SendMessage(chatId, textResolver.resolve("command.untrack.success"));
    }

    private SendMessage processUserCommand(Update update) {
        ListLinksResponse response = botService.listLinks(update.message().chat().id()).answer();
        if (response.links().isEmpty()) {
            return new SendMessage(update.message().chat().id(), textResolver.resolve("command.untrack.empty"));
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(
            response.links().stream()
                .map(link -> new InlineKeyboardButton[] {
                    new InlineKeyboardButton(link.url().toString()).callbackData(UNTRACK_DATA_PREFIX + link.id())})
                .toList()
                .toArray(new InlineKeyboardButton[0][0])
        );
        return new SendMessage(update.message().chat().id(),
            textResolver.resolve("command.untrack.main")
        ).replyMarkup(inlineKeyboardMarkup);
    }
}
