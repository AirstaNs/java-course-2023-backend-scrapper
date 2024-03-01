package edu.java.bot.manager;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.commands.Command;
import edu.java.bot.resolver.TextResolver;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserMessagesManager implements MessagesManager {
    private final TextResolver textResolver;
    private final List<Command> commands;

    @Autowired
    public UserMessagesManager(TextResolver textResolver, List<Command> commands) {
        this.textResolver = textResolver;
        this.commands = commands;
    }


    @Override
    public List<Command> commands() {
        return commands;
    }

    @Override
    public SendMessage process(Update update) {
        for (Command command : commands) {
            if (command.supports(update)) {
                return command.handle(update);
            }
        }
        if (update.message() == null) {
            return null;
        }
        String text = update.message().text();
        return new SendMessage(update.message().chat().id(),
            textResolver.resolve("message.unknown_command", Map.of("command", text != null ? text : "None"))
        );
    }

}
